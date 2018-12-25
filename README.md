
![alt text](https://lh3.googleusercontent.com/LUz9hmNpuK2C1Eu7RrY988_cRjtp35FeNSgedOoYFzByNhd2bhs22In4Ip7nvktrNypDvlyl5Bo0BQYCr8Bkzu5QpRQOR8M6vXvf=w1024-h768-rw-no)

```ruby
public static class TestModel {
        public int mPublicIntField;
        public String mPublicTextField;

        private int mPrivateIntField;
        private String mPrivateTextField;

        public int getmPrivateIntField() {
            return mPrivateIntField;
        }

        public String getmPrivateTextField() {
            return mPrivateTextField;
        }
    }
    
    TestModel testModel = parse.jsonTo("JSONSTRING",TestModel.class);
        if (testModel!=null)
        {
            mPublicIntField.setText("mPublicIntField : "+testModel.mPublicIntField);
            mPublicTextField.setText("mPublicIntField : "+testModel.mPublicTextField);
            mPrivateIntField.setText("mPublicIntField : "+testModel.getmPrivateIntField());
            mPrivateTextField.setText("mPublicIntField : "+testModel.getmPrivateTextField());
        }
        else
        {
            DialogBox.showOK("Json Exception!",null);
            mPublicIntField.setText("mPublicIntField : ");
            mPublicTextField.setText("mPublicIntField : ");
            mPrivateIntField.setText("mPublicIntField : ");
            mPrivateTextField.setText("mPublicIntField : ");
        }
```
