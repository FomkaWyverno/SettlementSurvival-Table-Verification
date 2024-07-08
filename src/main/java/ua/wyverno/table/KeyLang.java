package ua.wyverno.table;

public record KeyLang(String id, String a1Note, String sheetName) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyLang keyLang = (KeyLang) o;

        return id.equals(keyLang.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
