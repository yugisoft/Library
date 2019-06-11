package library.yugisoft.module;

 import android.content.Context;
 import android.graphics.Color;
 import android.graphics.Paint;
 import android.text.Layout;
 import android.view.Gravity;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.view.WindowManager;
 import android.view.animation.AccelerateDecelerateInterpolator;
 import android.view.animation.Animation;
 import android.view.animation.TranslateAnimation;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.ImageView;
 import android.widget.LinearLayout;
 import android.widget.ListPopupWindow;
 import android.widget.PopupWindow;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import java.util.ArrayList;
 import java.util.List;

 import library.yugisoft.module.Base.BaseDialog;
 import library.yugisoft.module.Utils.CustomBinding.BindingGridView;

public class ItemLooper<T>
{

        private int detailViewID;

        public ItemLooper() {
        this(yugi.activity);
}

public ItemLooper(Context context) {
        this(context, new ArrayList<>());
}

public ItemLooper(List<T> list) {
        this(yugi.activity, list);
}

public ItemLooper(Context context, List<T> list) {
        setContext(context);
        setList(list);
}

//region DECLARE
private Context context;
private List<T> list;
private LoopView loopView = null;
private int textSize = 12, drawItemsCount = 7, textAlignment = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, textColor = Color.BLACK, BackColor = Color.TRANSPARENT;
private boolean canLoop, showConfirm;
private Object parentObject;


private OnItemLooperSelected<T> onItemLooperSelected;

public OnItemLooperSelected<T> getOnItemLooperSelected() {
        return onItemLooperSelected;
}

public void setOnItemLooperSelected(OnItemLooperSelected<T> onItemLooperSelected) {
        this.onItemLooperSelected = onItemLooperSelected;
}
//endregion
//region Builds

public ItemLooper<T> setContext(Context context) {
        this.context = context;
        return this;
}

public ItemLooper<T> setList(List<T> list) {
        this.list = list;
        if (loopView == null)
       createLoopView();
        else
       loopView.setDataList(list);
        return this;
}

public ItemLooper<T> setCanLoop(boolean canLoop) {
        loopView = null;
        this.canLoop = canLoop;
        return this;
}

public ItemLooper<T> setTextSize(int textSize) {
        loopView = null;
        this.textSize = textSize;
        return this;
}

public ItemLooper<T> setDrawItemsCount(int drawItemsCount) {
        loopView = null;
        this.drawItemsCount = drawItemsCount;
        return this;
}

public ItemLooper<T> setTextAlignment(int textAlignment) {
        loopView = null;
        this.textAlignment = textAlignment;
        return this;
}

public ItemLooper<T> setTextColor(int textColor) {
        loopView = null;
        this.textColor = textColor;
        return this;
}

public ItemLooper<T> setBackColor(int backColor) {
        loopView = null;
        BackColor = backColor;
        return this;
}

public ItemLooper<T> setLoopView(LoopView loopView) {
        this.loopView = loopView;
        return this;
}

public ItemLooper<T> setParentObject(Object parentObject) {
        this.parentObject = parentObject;
        return this;
}

public ItemLooper<T> setShowConfirm(boolean showConfirm)
{
    this.showConfirm = showConfirm;
    return  this;
}

//endregion
//region Getters
public Context getContext() {
        return context;
}

public List<T> getList() {
        return list;
}

public boolean isCanLoop() {
        return canLoop;
}

public int getTextSize() {
        return textSize;
}

public int getDrawItemsCount() {
        return drawItemsCount;
}

public LoopView getLoopView() {
        return loopView;
}

public int getTextAlignment() {
        return textAlignment;
}

public int getTextColor() {
        return textColor;
}

public int getBackColor() {
        return BackColor;
}

public Object getParentObject() {
        return parentObject;
}

    public boolean isShowConfirm() {
        return showConfirm;
    }
//endregion

void createLoopView() {
        loopView = new LoopView(getContext());
        loopView.setCanLoop(isCanLoop());
        loopView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1F));
        loopView.setTextSize(getTextSize());
        loopView.setDrawItemsCount(getDrawItemsCount());
        loopView.setDataList(getList());
}

//region Popup
IL_Popup<T> popup;

public void show() {
        if (popup == null) {
       popup = new IL_Popup<T>(getContext(), this) {
      @Override
      public void Confirm() {
     if (getOnItemLooperSelected() != null) {
    getOnItemLooperSelected().onSelected(loopView.getSelectedItem(), getList().get(loopView.getSelectedItem()));
     }
      }
       };

        }
        popup.show();
}
//endregion

//region ListPopup
IL_ListPopup<T> listPopup;

public void show(View anchorView) {
        if (listPopup == null) {
       listPopup = new IL_ListPopup<T>(getContext(), this);
       listPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

     listPopup.dismiss();
     if (getOnItemLooperSelected() != null) {
    getOnItemLooperSelected().onSelected(position, getList().get(position));
     }
      }
       });
        }
        listPopup.setAnchorView(anchorView);
        listPopup.show();
        ;
}
//endregion

//region Dialog
IL_Dialog dialog;

    public void showDialog(String title) {
       showDialog(title,R.layout.view_item_looper);
    }
    public void showFullScreen(String title) {
        showFullScreen(title,R.layout.view_item_looper);
    }
    public void showDialog(String title,int id) {
        if (dialog == null)
        {
            dialog = new IL_Dialog(getContext(),id) {
                @Override
                public void onConfirm() {
                    if (getOnItemLooperSelected() != null) {
                        getOnItemLooperSelected().onSelected(loopView.getSelectedItem(), getList().get(loopView.getSelectedItem()));
                    }
                    dismiss();
                }
            };
            dialog.loop = loopView;
        } else {
            dialog.bar_loop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) yugi.activity.getResources().getDimension(R.dimen.dimen_loop_Bar_height), 0));
            dialog.dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        try {
            ((ViewGroup) loopView.getParent()).removeAllViews();
        } catch (Exception ex) { }

        dialog.bar_loop.addView(loopView);
        dialog.txt_dialog_title.setText(title);
        dialog.show();
    }
    public void showFullScreen(String title,int id) {
        if (dialog == null) {
            dialog = new IL_Dialog(getContext(),id) {
                @Override
                public void onConfirm() {
                    if (getOnItemLooperSelected() != null) {
                        getOnItemLooperSelected().onSelected(loopView.getSelectedItem(), getList().get(loopView.getSelectedItem()));
                    }
                    dismiss();
                }
            };
            dialog.loop = loopView;
        }

        dialog.dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.bar_loop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MATCH_PARENT, 1));
        try {
            ((ViewGroup) loopView.getParent()).removeAllViews();
        } catch (Exception ex) {
        }
        dialog.bar_loop.addView(loopView);
        dialog.txt_dialog_title.setText(title);
        dialog.show();
    }


public static int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

public void showDialog(String title, int witdh, int height) {
        showDialog(title);
        dialog.bar_loop.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1));
        dialog.dialog.getWindow().setLayout(witdh, height);
}


//endregion



//region ListDialog

IL_Dialog listDialog = null;
BindingGridView bindingGridView = null;

        public BindingGridView showListDialog(String title) { return  showListDialog(title,R.layout.view_item_looper); }

        public BindingGridView showListFullDialog(String title) { return showListFullDialog(title,R.layout.view_item_looper); }

        public BindingGridView showListDialog(String title,int id) {
        if (listDialog == null)
            listDialog = new IL_Dialog(getContext(),id);
        listDialog.dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        listDialog.bar_loop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) yugi.activity.getResources().getDimension(R.dimen.dimen_loop_Bar_height), 0));

            listDialog.btn_confirm.setVisibility(isShowConfirm() ? View.VISIBLE : View.GONE);

        if (bindingGridView == null)
            bindingGridView = new BindingGridView(getContext());
        bindingGridView.setDetailViewID(getDetailViewID());
        bindingGridView.setData(getList());

        bindingGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getOnItemLooperSelected() != null)
                    getOnItemLooperSelected().onSelected(position,getList().get(position));
                listDialog.dismiss();
            }
        });

        listDialog.bar_loop.removeAllViews();
        listDialog.bar_loop.addView(bindingGridView);

        listDialog.txt_dialog_title.setText(title);
        listDialog.show();
        return bindingGridView;
    }

        public BindingGridView showListFullDialog(String title,int id) {
        if (listDialog == null)
            listDialog = new IL_Dialog(getContext(),id);
        listDialog.dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        listDialog.bar_loop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        listDialog.btn_confirm.setVisibility(isShowConfirm() ? View.VISIBLE : View.GONE);
        if (bindingGridView == null)
            bindingGridView = new BindingGridView(getContext());
        bindingGridView.setDetailViewID(getDetailViewID());
        bindingGridView.setData(getList());

        bindingGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getOnItemLooperSelected() != null)
                    getOnItemLooperSelected().onSelected(position,getList().get(position));
                listDialog.dismiss();
            }
        });

        listDialog.bar_loop.removeAllViews();
        listDialog.bar_loop.addView(bindingGridView);

        listDialog.txt_dialog_title.setText(title);
        listDialog.show();
        return bindingGridView;
    }

        public int getDetailViewID() {
                return detailViewID;
        }

        public ItemLooper<T>  setDetailViewID(int detailViewID) {
                this.detailViewID = detailViewID;
                return  this;
        }

        private T SelectedITem;



    public T getSelectedITem() {
        return SelectedITem;
    }

    public void setSelectedITem(T selectedITem) {
        SelectedITem = selectedITem;
        getOnItemLooperSelected().onSelected(getList().indexOf(selectedITem),selectedITem);
    }

//endregion

public interface OnItemLooperSelected<T> {
        void onSelected(int index, T Item);
}
 }

 class IL_ListPopup<T> extends ListPopupWindow {
int layout = R.layout.view_item_looper;

private ItemLooper<T> looper;

ItemAdapter<T> adapter;

public IL_ListPopup(ItemLooper<T> looper) {
        this(yugi.activity, looper);
}

public IL_ListPopup(Context context, ItemLooper<T> looper) {
        super(context);
        this.looper = looper;
        adapter = new ItemAdapter<T>(context) {

       @Override
       public long getItemId(int i) {
      if (getItem(i) instanceof IItemLooper)
     return ((IItemLooper) getItem(i)).toLooperLong();
      else
     return 0;
       }

       @Override
       public View getView(int i, View view, ViewGroup viewGroup) {
      Object item = getList().get(i);
      if (view == null) {
     TextView tview = new TextView(context);
     tview.setTextSize(IL_ListPopup.this.looper.getTextSize());
     tview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
     tview.setGravity(looper.getTextAlignment());
     tview.setText(item instanceof IItemLooper ? ((IItemLooper) item).toLooperString() : item.toString());
     tview.setTextColor(looper.getTextColor());
     tview.setBackgroundColor(looper.getBackColor());
     tview.setPadding(5,5,5,5);
     view = tview;
      } else {
     TextView tview = (TextView) view;
     tview.setText(item instanceof IItemLooper ? ((IItemLooper) item).toLooperString() : item.toString());
      }

      return view;
       }
        };
        setAdapter(adapter);
        adapter.setList(looper.getList());
        this.setModal(true);
}
 }

 class IL_Dialog extends BaseDialog {

public IL_Dialog() {
        this(yugi.activity);
}

public IL_Dialog(Context context) {
        super(context, R.layout.view_item_looper);
}
     public IL_Dialog(int id) {
         super(yugi.activity, id);
     }
     public IL_Dialog(Context context,int id) {
         super(context, id);
     }
//region DECLARE

public TextView txt_dialog_title;
public View btn_confirm;
public View btn_cancel;
public LoopView loop;
public LinearLayout bar_loop;
//endregion


@Override
public void Initilialize() {

        txt_dialog_title = (TextView) itemView.findViewById(R.id.txt_dialog_title);
        btn_confirm = itemView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(p -> onConfirm());
        btn_cancel = itemView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(p -> onCancel());

        bar_loop = (LinearLayout) itemView.findViewById(R.id.bar_loop);
}


public void onConfirm() {
}

;

public void onCancel() {
        dismiss();
}

;

 }

 class IL_Popup<T> extends PopupWindow {
//region DECLARE

public LinearLayout container_picker;
public RelativeLayout container_toolbar;
public Button btn_cancel;
public Button btn_confirm;
public LinearLayout ly_pickers;
public View itemView;
//endregion

public void Confirm() {
}

private ItemLooper<T> looper;

ItemAdapter<T> adapter;

public IL_Popup(ItemLooper<T> looper) {
        this(yugi.activity, looper);
}

public IL_Popup(Context context, ItemLooper<T> looper) {
        super(context);
        this.looper = looper;
        itemView = LayoutInflater.from(yugi.activity).inflate(R.layout.item_looper_popup_layout, null);
        container_picker = (LinearLayout) itemView.findViewById(R.id.container_picker);
        container_toolbar = (RelativeLayout) itemView.findViewById(R.id.container_toolbar);
        btn_cancel = (Button) itemView.findViewById(R.id.btn_cancel);
        btn_confirm = (Button) itemView.findViewById(R.id.btn_confirm);
        ly_pickers = (LinearLayout) itemView.findViewById(R.id.ly_pickers);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
      Confirm();
      dismiss();
       }
        });
        btn_cancel.setOnClickListener(p -> dismiss());


        setTouchable(true);
        setFocusable(true);
        setAnimationStyle(R.style.FadeInPopWin);


        setContentView(itemView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

}

public void show() {
        try {
       ((ViewGroup) looper.getLoopView().getParent()).removeAllViews();
        } catch (Exception ex) {
        }
        ly_pickers.addView(looper.getLoopView());
        TranslateAnimation trans = new TranslateAnimation(
       Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
       0, Animation.RELATIVE_TO_SELF, 1,
       Animation.RELATIVE_TO_SELF, 0);

        showAtLocation(yugi.activity.getWindow().getDecorView(), Gravity.BOTTOM,
       0, 0);
        trans.setDuration(400);
        trans.setInterpolator(new AccelerateDecelerateInterpolator());

        container_picker.startAnimation(trans);
}
 }
 
 interface IItemLooper {
default String toLooperString() {
        return this.toString();
}

;

default int toLooperInt() {
        return 0;
}

default long toLooperLong() {
        return 0;
}
 }
