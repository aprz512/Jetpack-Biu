package com.aprz.jetpack_biu.paging3.model

/**
 * Sealed class（密封类） 是一个有特定数量子类的类，看上去和枚举有点类似，所不同的是，在枚举中，我们每个类型只有一个对象（实例）；而在密封类中，同一个类可以拥有几个对象。
 * Sealed class（密封类）的所有子类都必须与密封类在同一文件中
 * Sealed class（密封类）的子类的子类可以定义在任何地方，并不需要和密封类定义在同一个文件中
 * Sealed class（密封类）没有构造函数，不可以直接实例化，只能实例化内部的子类
 */
sealed class ArticleModel {
    data class ArticleItem(val article: Article) : ArticleModel()
    data class SeparatorItem(val title: String) : ArticleModel()
}