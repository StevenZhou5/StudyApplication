package study.zhouzhenwu.com.mydemo.android.aidl;

import study.zhouzhenwu.com.mydemo.android.aidl.Book;

interface IBookManager{
List<Book> getBookList();
void addBook(in Book book);

}