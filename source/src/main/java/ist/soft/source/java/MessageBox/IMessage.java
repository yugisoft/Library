package ist.soft.source.java.MessageBox;

public interface IMessage
{
    default String getTitle(){ return "";}
    default String getDescription(){return  "";}
}
