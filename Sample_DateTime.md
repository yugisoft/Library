![alt text](https://lh3.googleusercontent.com/8k4lkURWBwWI9c8ka6KcAIJzJZDR5yL3B651BUAXtWhQFZxSX14b9T69cShaTlKMYk-Z3fJvU3SEPrsunFPm03L7EsViuiqrtBbnaw=w1080-h1920-rw-no)

```ruby
private void displayDateTime(DateTime dateTime)
    {
        txt_iso8061.setText(dateTime.getIso8601());
        edit_iso8601.setText(dateTime.getIso8601());
        txt_long_date.setText(dateTime.toLongDateString());
        txt_short_date.setText(dateTime.toShortDateString());
        txt_long_time.setText(dateTime.toLongTimeString());
        txt_short_time.setText(dateTime.toShortTimeString());
        txt_long_datetime.setText(dateTime.toLongDateTimeString());
        txt_short_datetime.setText(dateTime.toShortDateTimeString());
    }
    
   button.setOnClickListener(p-> displayDateTime(DateTime.fromISO8601UTC(edit_iso8601.getText().toString())));
```
