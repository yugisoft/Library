package library.yugisoft.module.Utils.CustomBinding;

public @interface BindingBoolean
{
    public String EnableText()  default "Aktif";
    public String DisableText() default "Pasif";
    public BindingBooleanType BooleanType() default BindingBooleanType.TEXT_TYPE;
    public enum BindingBooleanType {
        BOOLEAN_TYPE,TEXT_TYPE
    }
}
