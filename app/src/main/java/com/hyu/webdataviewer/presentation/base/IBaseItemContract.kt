package com.hyu.webdataviewer.presentation.base


interface IBaseItemContract{
    interface Presenter<T>{
        fun setItemModel(model: T, itemClickListener : ItemClickListener<T>?)
    }

    interface View<T>{
        /**
         * bindModel View Holder
         */
        fun bindModel(model: T, itemClickListener : ItemClickListener<T>?)

        fun showItemView(model: T, itemClickListener :ItemClickListener<T>?)

        /**
         * this function called that view detatched
         */
        fun unbind()
    }

    /**
     * List Item view Click Listner
     */
    interface ItemClickListener<T>{
        fun onClick(index : Int, model : T)
    }
}