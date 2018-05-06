package im.admin.generator.enums;

public enum BeanEnum {
    VARCHAR("@1"),
    INT("@2"),
    RADIO_BOOLEAN("@3"),
    SELECT_INT("@4"),
    SELECT_STRING("@5"),
    EDITOR("@6"),
    FILE("@7"),
    CHECKBOX("@8"),
    IGNORE("@9"),
    IMAGE("@A");

    private String value;

    BeanEnum(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println("BeanEnum value = " + BeanEnum.IGNORE.getValue());
    }

    public String getValue() {
        return value;
    }
}