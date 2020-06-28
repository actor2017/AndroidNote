DecimalFormat ,DecimalFormat是NumberFormat一个具体的子类,主要是格式化十进制数

private String formatToString(T item) {
    if (item instanceof Float || item instanceof Double) {
        //DecimalFormat.getInstance().format();
        return new DecimalFormat("0.00").format(item);//java.text.DecimalFormat
    }
    return item.toString();
}