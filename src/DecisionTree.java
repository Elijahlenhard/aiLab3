import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DecisionTree implements Serializable {

    dtNode root;
    int depth;


    public DecisionTree(int depth, List<Case> cases){
        root = new dtNode();
        this.depth = depth;
        root.cases = cases;

        buildTree(root);
        int test = 1;
    }

    private void buildTree(dtNode node){
        if(node.depth>=depth){
            return;
        }
        double best = 1;
        int bestIndex = -1;
        for (int i = 0; i < 5; i++) {
            if(!node.checked[i]){
                Double currentError = getError(node, i);
                if (currentError< best){
                    best = currentError;
                    bestIndex = i;
                }
            }
        }
        if (bestIndex==-1) return;

        node.nextDecision = bestIndex;
        node.trueChild = new dtNode();
        node.falseChild = new dtNode();

        for (int i = 0; i < node.checked.length; i++) {
            node.falseChild.checked[i] = node.checked[i];
            node.trueChild.checked[i] = node.checked[i];
        }

        node.trueChild.checked[node.nextDecision] = true;
        node.falseChild.checked[node.nextDecision] = true;

        node.trueChild.depth = node.depth+1;
        node.falseChild.depth = node.depth+1;


        for (int i = 0; i < node.cases.size(); i++) {
            Case current = node.cases.get(i);
            if(current.data[node.nextDecision]){
                node.trueChild.cases.add(current);
            } else{
                node.falseChild.cases.add(current);
            }
        }

        buildTree(node.falseChild);
        buildTree(node.trueChild);


    }

    private Double getError(dtNode node, int attribute){

        double total = node.cases.size();
        double totalAttrTrue =0;
        double totalAttrFalse=0;
        double attrTrueOutTrue=0;
        double attrTrueOutFalse=0;
        double attrFalseOutTrue=0;
        double attrFalseOutFalse=0;

        for (Case current: node.cases){
            if(current.data[attribute]){
                totalAttrTrue++;
                if(current.isEng)
                    attrTrueOutTrue++;
                else
                    attrTrueOutFalse++;
            } else {
                totalAttrFalse++;
                if(current.isEng)
                    attrFalseOutTrue++;
                else
                    attrFalseOutFalse++;
            }
        }

        double totalRight =0;

        double attrTrueRatio = attrTrueOutTrue/totalAttrTrue;
        if(attrTrueRatio >.5){
            totalRight+= attrTrueOutTrue;
        } else {
            totalRight += attrTrueOutFalse;
        }
        double attrFalseRatio = attrFalseOutTrue/totalAttrFalse;
        if (attrFalseRatio>.5){
            totalRight += attrFalseOutTrue;
        } else {
            totalRight += attrFalseOutFalse;
        }

        return 1-(totalRight/total);

    }


    public boolean checkCase(Case input){

        dtNode current = root;

        dtNode second;
        if(input.data[current.nextDecision]){
            second=current.trueChild;
        } else{
            second = current.falseChild;
        }


        while(!current.isLeaf()){
            if(input.data[current.nextDecision]){
                current=current.trueChild;
            } else{
                current = current.falseChild;
            }
        }


        return current.getOutput()==input.isEng;

    }
    public String getDecision(Case input){
        dtNode current = root;

        dtNode second;
        if(input.data[current.nextDecision]){
            second=current.trueChild;
        } else{
            second = current.falseChild;
        }


        while(!current.isLeaf()){
            if(input.data[current.nextDecision]){
                current=current.trueChild;
            } else{
                current = current.falseChild;
            }
        }


        if(current.getOutput()){
            return "en";
        } else {
            return "nl";
        }
    }
}


class dtNode implements Serializable{
    int nextDecision;
    dtNode trueChild;
    dtNode falseChild;
    boolean decision;
    int numTrue;
    int numFalse;
    boolean[] checked = new boolean[5];
    List<Case> cases = new ArrayList<>();
    int depth=0;

    public int getNumTrue(){
        int total =0;
        for (Case current:cases){
            if(current.isEng)
                total++;
        }
        numTrue = total;
        return total;
    }

    public boolean isLeaf(){
        return trueChild == null;
    }

    public boolean getOutput(){
        return ((double)getNumTrue())/(double)this.cases.size()>.5;
    }





}