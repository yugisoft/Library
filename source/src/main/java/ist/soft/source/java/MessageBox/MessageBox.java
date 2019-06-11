package ist.soft.source.java.MessageBox;

import android.app.AlertDialog;
import android.content.Context;

public class MessageBox extends AlertDialog implements IMessage
{
    private String title,description;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }



    protected MessageBox(Context context){this(context,"","");}
    protected MessageBox(Context context, String title){this(context,title,"");}
    protected MessageBox(Context context, String title, String description)
    {
        super(context);
        this.title = title;
        this.description = description;
    }

}
