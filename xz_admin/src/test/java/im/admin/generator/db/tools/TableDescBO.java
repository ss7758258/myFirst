package im.admin.generator.db.tools;
import java.io.Serializable;
/**
 * 数据库表结构情况BO
 */
public class TableDescBO implements Serializable {
    /**
     * 数据库表中对应的字段名称
     */
    private String field;
    /**
     * 数据库表中对应字段的类型
     */
    private String type;
    /**
     * 数据库表中字段是否为空：YES/NO
     */
    private String isNullable;
    /**
     * 是否为主键：KEY，不是，则为空，null
     */
    private String key;
    /**
     * 字段的默认值
     */
    private String isDefault;
    /**
     * 额外的属性，如：auto_increment
     */
    private String extra;
    /**
     * 小数位数
     */
    private String numericScale;
    /**
     * 数字长度
     */
    private String munericLength;
    /**
     * 字符长度
     */
    private String characterLength;
    /**
     * 备注
     */
    private String memo;
    /**
     * 重写toStirng方法 主要是为了控制台输出
     */
    public String toString() {
        String str="";
        if(field==null)field="NULL";
        if(type==null)type="NULL";
        if(isNullable==null)isNullable="NULL";
        if(key==null)key="NULL";
        if(isDefault==null)isDefault="NULL";
        if(extra==null)extra="NULL";
        if(memo==null)memo="NULL";
        str = str + field + (field.length()>=8?"\t":"\t\t");
        str = str + type + (type.length()>=8?"\t":"\t\t");
        str = str + isNullable + (isNullable.length()>=8?"\t":"\t\t");
        str = str + key + (key.length()>=8?"\t":"\t\t");
        str = str + isDefault + (isDefault.length()>=8?"\t":"\t\t");
        str = str + extra + (extra.length()>=8?"\t":"\t\t");
        str = str + memo + (memo.length()>=8?"\t":"\t\t");
        return str;
        //return field + "\t\t" + type + "\t\t" + isNullable + "\t\t" + key + "\t\t" + isDefault + "\t\t" + extra + "\t\t"+ memo;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getIsNullable() {
        return isNullable;
    }
    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    public String getExtra() {
        return extra;
    }
    public void setExtra(String extra) {
        this.extra = extra;
    }
    public String getNumericScale() {
        return numericScale;
    }
    public void setNumericScale(String numericScale) {
        this.numericScale = numericScale;
    }
    public String getMunericLength() {
        return munericLength;
    }
    public void setMunericLength(String munericLength) {
        this.munericLength = munericLength;
    }
    public String getCharacterLength() {
        return characterLength;
    }
    public void setCharacterLength(String characterLength) {
        this.characterLength = characterLength;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}