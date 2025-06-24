#include <bits/stdc++.h>
using namespace std;

template <class T>
class Node
{
public:
    T value;
    Node<T> *next;
    Node<T> *prev;
    Node(T x = NULL, Node<T> *N = NULL, Node<T> *P = NULL)
    {
        value = x;
        next = N;
        prev = P;
    }
};

template <class T>
class myList
{
    Node<T> *head = NULL;
    Node<T> *tail = NULL;
    int n = 0;
public:
    void push_back(T x)
    {
        Node<T> *temp = new Node<T>(x);
        if (n == 0)
        {
            head = tail = temp;
            n = 1;
        }
        else
        {
            tail->next = temp;
            temp->prev = tail;
            tail = temp;
            n++;
        }
    }
    void push_head(T x)
    {
        Node<T> *temp = new Node<T>(x);
        if (n == 0)
        {
            head = tail = temp;
            n = 1;
        }
        else
        {
            head->prev = temp;
            temp->next = head;
            head = temp;
            n++;
        }
    }
    void pop_back()
    {
        if (n == 0)
            return;
        if (n == 1)
        {
            head = tail = NULL;
            n--;
            return;
        }
        Node<T> *old_tail = tail;
        tail = tail->prev;
        tail->next = NULL;
        delete old_tail;
        n--;
    }
    void pop_head()
    {
        if (n == 0)
            return;
        if (n == 1)
        {
            head = tail = NULL;
            n--;
            return;
        }
        Node<T> *old_head = head;
        head = head->next;
        head->prev = NULL;
        delete old_head;
        n--;
    }

    void print()
    {
        Node<T> *it = head;
        while (it != NULL)
        {
            cout << it->value << " ";
            it = it->next;
        }
    }

    void sortlist() 
    {
        Node<T> *i, *j;
        T temp_value;
        for (i = head; i != nullptr; i = i->next) {
            for (j = i->next; j != nullptr; j = j->next) {
                if (i->value < j->value) {
                    temp_value = i->value;
                    i->value = j->value;
                    j->value = temp_value;
                }
            }
        }
    }
    Node<int> *head1(){
        return head;
    }
};


class Bo_Test{
    private:
        string Cha, Con;
        int Tuoi_Sinh;
    public:
        Bo_Test(){
            Cha = "Ted";
            Con = "A";
            Tuoi_Sinh=0;
        }
        Bo_Test(string Cha, string Con, int Tuoi_Sinh){
            this->Cha=Cha;
            this->Con=Con;
            this->Tuoi_Sinh=Tuoi_Sinh;
        }
        friend istream &operator >> (istream &cin, Bo_Test & a){
            cin >> a.Cha >> a.Con >> a.Tuoi_Sinh;
            return cin;
        }
        string Ten_Cha(){
            return Cha;
        }
        string Ten_Con(){
            return Con;
        }
        int Tuoi_Sin(){
            return Tuoi_Sinh;
        }
};

int main() {
    int n;
    cin >> n;

    for (int i = 1; i <= n; i++) {
        int so_nguoi;
        cin >> so_nguoi;
        myList<int> tuoi;
        Bo_Test a[so_nguoi];
        for (int i = 0; i < so_nguoi; ++i) {
            cin >> a[i];
        }
        string h= "Ted";
        int tuoic = 100;
        string v[100];
        int tuoich[100];
        v[1]="Ted";
        tuoich[1]=100;
        int h1=1;
        map <string, int> Nguoi_Con;
        for(int j=0;j<=so_nguoi;){
            for(int y=1;y<=h1;y++){
                h=v[y];
                tuoic=tuoich[y];
                int dem = 1;
                for (int i = 0; i < so_nguoi; i++){
                    if(a[i].Ten_Cha()==h){
                        int m = tuoic - a[i].Tuoi_Sin();
                        Nguoi_Con[a[i].Ten_Con()]=m;
                        tuoi.push_back(m);
                        v[dem]=a[i].Ten_Con();
                        tuoich[dem]=m;
                        dem++;
                    }
                }
                h1=dem;
                j++;
            } 
        }
        tuoi.sortlist();
        cout << "DATASET " << i << endl;
        for(int i=Nguoi_Con.size(); i>0; i--) {
            for (auto x:Nguoi_Con) {
                if (x.second == tuoi.head1()->value) {
                    cout << x.first << " " << x.second<< endl;
                    Nguoi_Con.erase(x.first);
                }
            }
            tuoi.pop_head();

        }
    }
    return 0;
}

