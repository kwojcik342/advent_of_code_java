import java.util.LinkedList;

public class Num {
    private int num;
    private LinkedList<Integer> positions;

    public Num(){
        this.positions = new LinkedList<>();
    }

    public void addPosition(int position){
        this.positions.add(position);
    }

    public void setNum(int num){
        this.num = num;
    }

    public int getNum(){
        return this.num;
    }

    public String toString(){
        String ret = this.num + ": ";

        for(int position : this.positions){
            ret += position + ",";
        }

        return ret;
    }

    public boolean isAdjacent(int position){
        for(int p : this.positions){
            if(p == position){
                return true;
            }
        }

        if (position == (this.positions.get(0) - 1)) {
            return true;
        }

        if(position == (this.positions.get(this.positions.size()-1) + 1)){
            return true;
        }

        return false;
    }
}
