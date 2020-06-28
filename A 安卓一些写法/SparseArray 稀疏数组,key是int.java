SparseArray<E> implements Cloneable, 相当于一个Map, android特有, 主要是替换Map, 效率比Map高很多

private SparseArray<Fragment> array = new SparseArray<>();//key = int, value = Object
//增
array.append(position, fragment);//?
array.put(position, fragment);//先查找key的位置,如果当前key已经存在,直接覆盖对应的value.如果没有,但要插入的位置上的元素为DELETED,就直接用新key,value取代原有的.否则就直接使用insert进行插入。
array.setValueAt(position, fragment);
//删
array.delete(position);
array.remove(position);//调的 delete
array.removeAt(position);
array.clear();
//查
Fragment fragment = array.get(position);
Fragment fragment = array.get(position, fragment);
int index = array.keyAt(position);//key所在位置, 找不到时返回小于0的数值，而不是返回-1
Fragment fragment = array.valueAt(position);


https://www.jianshu.com/p/081b78dfe9f6
1.在写SparseArray某些情况下比HashMap性能更好，按照官方问答的解释，主要是因为SparseArray不需要对key和value进行auto-boxing（将原始类型封装为对象类型，比如把int类型封装成Integer类型），
结构比HashMap简单（SparseArray内部主要使用两个一维数组来保存数据，一个用来存key，一个用来存value）不需要额外的额外的数据结构

key为int的时候才能使用，注意是int而不是Integer，这也是sparseArray效率提升的一个点，去掉了装箱的操作

2.插入
100000条数据的存储使用DDMS查看，hashMap的存储空间14M左右，而SparseArray自由8M多几乎是少了40%接近
插入时候，SparseArray 正序插入效率比起倒序插入快了几乎是10倍， hashMap差不多。

我们是按照1,3,2的顺序排列的，但是在SparseArray内部还是按照正序排列的，这时因为SparseArray在检索数据的时候使用的是二分查找，所以每次插入新数据的时候SparseArray都需要重新排序，所以代码4中，逆序是最差情况。

3.SparseArray原理
单纯从字面上来理解，SparseArray指的是稀疏数组(Sparse array)，所谓稀疏数组就是数组中大部分的内容值都未被使用（或都为零），在数组中仅有少部分的空间使用。因此造成内存空间的浪费，为了节省内存空间，并且不影响数组中原有的内容值，我们可以采用一种压缩的方式来表示稀疏数组的内容。

4.它仅仅提高内存效率，而不是提高执行效率

