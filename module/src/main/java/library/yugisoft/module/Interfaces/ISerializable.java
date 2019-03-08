package library.yugisoft.module.Interfaces;

import library.yugisoft.module.parse;

public interface ISerializable
{
    default <T> T Deserialize(String json)
    {
        return  Deserialize(json,"");
    }
    default <T> T Deserialize(String json,String key){
        return  Deserialize(json,key,0);
    }
    default <T> T Deserialize(String json,String key,int index) {
        parse.jsonTo(this,json,key,index);
        return (T)this;
    }
    default <T> T DeserializeNew(String json)
    {
        return DeserializeNew(json,"");
    }
    default <T> T DeserializeNew(String json,String key){
        return DeserializeNew(json,key,0);
    }
    default <T> T DeserializeNew(String json,String key,int index) {
        String Json =parse.toJson(parse.jsonTo(json,key,index,this.getClass()));
        parse.jsonTo(this,Json,"",0);
        return (T)this;
    }
    default String Serialize()
    {
        return  parse.toJson(this);
    }




}
