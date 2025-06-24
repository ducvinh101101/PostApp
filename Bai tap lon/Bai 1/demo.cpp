#ifndef TUDIEN_cpp
#define TUDIEN_cpp
#include "hashtbl.cpp"
#include <bits/stdc++.h>
using namespace std;
int hash_string(string str, int m = 997)
{
    int hash = 0;
    for (int i = 0; str[i]; i++)
    {
        hash = (hash * 31 + str[i]) % m;
    }
    return hash;
}
    
class TuDien{
        private:
            int m = 997;
            Hashtable<string, string> dict;
        public:
        TuDien();
        void docfile(){
            ifstream input("input.txt");
            string temp;
            getline(input, temp);
            int n = stoi(temp);
            for (int i = 0; i < n; i++)
            {
                string v, a;
                getline(input, v);
                getline(input, a);
                dict.Add(v, a, hash_string);
            }
        }
        void search(){
            cout << "Nhap tu can tim: ";
            string s;
            cin >> s;
            Node<string, string> *node = dict.Find(s, hash_string);
            if (node != NULL)
            {
                cout << "Nghia cua tu can tim la: " << node->getElem() << endl;
            }
            else
            {
                cout << "Khong co tu can tim trong tu dien" << endl;
            }
        }
        void suatu(){
             cout << "Nhap so luong tu can doi:";
            int e2;
            cin >> e2;
            cin.ignore();
            for (int i = 0; i < e2; i++)
            {
                string v;
                cout << "Nhap tu can doi:";
                getline(cin, v);
                if (dict.Contains(v, hash_string))
                {
                    cout << "Tu can doi co trong tu dien" << endl<< "Doi thanh:" << endl;
                    string v1, a1;
                    cout << "Nhap tu Anh:";
                    getline(cin, v1);
                    cout << "Nhap tu Viet:";
                    getline(cin, a1);
                    Node<string, string> *nodenew = new Node<string, string>();
                    nodenew->setKey(v1);
                    nodenew->setElem(a1);
                    Node<string, string> *nodereplace = new Node<string, string>();
                    nodereplace = dict.Find(v, hash_string);
                    dict.Replace(nodenew, nodereplace, hash_string);
                }
                else
                {
                    cout << "Tu can doi khong co trong tu dien" << endl;
                }
            }
        }
        void add(){
            cout << "Nhap so luong tu can them: ";
            int e;
            cin >> e;
            cin.ignore();
            for (int i = 0; i < e; i++)
            {
                string v, a;
                cout << "Nhap tu Anh:";
                getline(cin, v);
                cout << "Nhap tu Viet:";
                getline(cin, a);
                dict.Add(v, a, hash_string);
            }
        }
        void xoa(){
            cout << "Nhap so luong tu can xoa:";
            int e1;
            cin >> e1;
            cin.ignore();
            for (int i = 0; i < e1; i++)
            {
                string v;
                cout << "Nhap tu can xoa:";
                getline(cin, v);
                if (dict.Contains(v, hash_string))
                {
                    cout << "Tu can xoa co trong tu dien" << endl;
                    dict.Remove(v, hash_string);
                }
                else
                {
                    cout << "Tu can xoa khong co trong tu dien" << endl;
                }
            }
        }
        void xuatfile(){
            ofstream output("output.txt");
            dict.outp();
        }
};
#endif