#ifndef HASHTABLE_cpp
#define HASHTABLE_cpp
#include "slist.cpp"
template <class Keys, class T>
class Hashtable
{
private:
	int m;
	SingleList<Keys, T> *table;

public:
	Hashtable(int n);
	~Hashtable()
	{
		delete[] table;
	}
	Node<Keys, T> *Add(Keys key, T e, int (*hash)(Keys, int));
	void Remove(Keys key, int (*hash)(Keys, int));
	void Replace(Node<Keys, T> *q, Node<Keys, T> *p, int (*hash)(Keys, int));
	Node<Keys, T> *Find(Keys key, int (*hash)(Keys, int));
	bool Contains(Keys key, int (*hash)(Keys, int));
	int Count();
	void outp();
};
template <class Keys, class T>
Hashtable<Keys, T>::Hashtable(int n)
{
	m = n;
	table = new SingleList<Keys, T>[m];
}
template <class Keys, class T>
Node<Keys, T> *Hashtable<Keys, T>::Add(Keys key, T e, int (*hash)(Keys, int))
{
	Node<Keys, T> *p;
	int h = hash(key, m); // hash la mot con tro ham
	p = table[h].getNode(key);
	if (p == NULL)
		return table[h].pushback(key, e);
	else
		return NULL;
}
template <class Keys, class T>
void Hashtable<Keys, T>::Remove(Keys key, int (*hash)(Keys, int))
{
	int h = hash(key, m);
	Node<Keys, T> *p;
	p = table[h].getNode(key);
	if (p != NULL)
		table[h].remove(p);
}
template <class Keys, class T>
Node<Keys, T> *Hashtable<Keys, T>::Find(Keys key, int (*hash)(Keys, int))
{
	int h = hash(key, m);
	return table[h].getNode(key);
}
template <class Keys, class T>
bool Hashtable<Keys, T>::Contains(Keys key, int (*hash)(Keys, int))
{
	int h = hash(key, m); // hash la mot con tro ham
	return table[h].getNode(key) != NULL;
}
template <class Keys, class T>
int Hashtable<Keys, T>::Count()
{
	int t = 0;
	for (int i = 0; i < m; i++)
		t = t + table[i].size();
	return t;
}

template <class Keys, class T>
void Hashtable<Keys, T>::Replace(Node<Keys, T> *q, Node<Keys, T> *p, int (*hash)(Keys, int)) // Thay the hai node trong bang bam
{
	int h = hash(p->getKey(), m);
	p = table[h].getNode(p->getKey());
	if (p != NULL)
		table[h].replace(p, q->getKey(), q->getElem());
}
template <class Keys, class T>
void Hashtable<Keys, T>::outp() // xuat ra file output
{	
	ofstream output("output.txt");
for (int i = 0; i < m; i++) {
    Node<string, string>* p = table[i].getHead();
    while (p != NULL) {
        output << p->getKey() << " : " << p->getElem() << "\n";
        p = p->getNext();
    }
}
}
#endif