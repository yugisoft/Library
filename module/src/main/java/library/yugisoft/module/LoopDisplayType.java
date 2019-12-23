package library.yugisoft.module;

public enum LoopDisplayType {
    popup(0),
    listPopup(1),
    Dialog(2),
    FullScreenDialog(3),
    ListDialog(4),
    FullScreenListDialog(5);

    int id;
    private LoopDisplayType(int id){
        this.id = id;
    }

    public boolean Compare(int i){return id == i;}
    public static LoopDisplayType GetValue(int _id)
    {
        LoopDisplayType[] As = LoopDisplayType.values();
        for(int i = 0; i < As.length; i++) {
            if(As[i].Compare(_id))
                return As[i];
        }
        return LoopDisplayType.popup;
    }
}
