#ifndef NODE_CPP
#define NODE_CPP
template <class Keys,class T>
class Node{
	private:
	    Keys key;   
		T elem;
		Node<Keys,T> *next;
	public:
		Node() { next = 0; }
		Keys getKey(){ return key;}
		void setKey(Keys k){ key = k;}
		Node<Keys,T>  *getNext()          //Tra lai dia chi cua doi tuong do thanh phan Next tro den
		{
			return next;
		}
		void    setNext(Node<Keys,T> *p)		//Dat thanh phan Next tro vao doi tuong la doi cua phuong thuc
		{
				next = p;
		}
		T       getElem()			// Tra lai dia chi cua phan tu luu trong Node
		{
			return elem;
		}
		void    setElem(T e)	// Dat thay the phan tu luu trong Node bang phan tu moi
		{
			elem = e;
		}
	};
#endif