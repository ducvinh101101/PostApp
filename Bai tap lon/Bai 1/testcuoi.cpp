#include <bits/stdc++.h>
#include "demo.cpp"
using namespace std;

int main()
{
    TuDien a;
    int luachon=0;
    do
    {
        cout << "Lua chon:\n";
        cout << "1. Doc file tu tep input.\n";
        cout << "2. Tim kiem.\n";
        cout << "3. Sua doi tu.\n";
        cout << "4. Them tu moi.\n";
        cout << "5. Xoa tu.\n";
        cout << "6. Luu tu dien vao tep output.\n";
        cout << "7. Thoat\n";
        cout << "Nhap lua chon: ";
        cin >> luachon;
        switch (luachon)
        {
        case 1:
        {
            a.docfile();
            break;
        }
        case 2:
        {
            a.search();
            break;
        }
        case 3:
        {
            a.suatu();
            break;
        }
        case 4:
        {
            a.add();
            break;
        }
        case 5:
        {
            a.xoa();
            break;
        }
        case 6:
        {
            a.xuatfile();
            break;
        }
        case 7:
        {
            cout << "Chuong trinh ket thuc!!!!";
            break;
        }
        }
    } while (luachon >= 1 && luachon <= 6);

    return 0;
}