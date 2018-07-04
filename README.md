# DataTable



DataTable
-Json to Table
-Table to Json
-Json to Model
-Model to Json


String json = "[{"a":"b"},{"a":"d"}]";
DataTable dt = new DataTable(json);

String ret_json = DataTable.getJsonData(); // .getJsonData([RowIndex]);

class mdl
{
  public String a
}

List<mdl> list = new SmartList(mdl.class);

dt.ToClass(dt);

mdl m = new  mdl();

dt.ToClass(m,0);


DataTable mdt = DataTable.ToTable(m);

mdt.getJsonData();


