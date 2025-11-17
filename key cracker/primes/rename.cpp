#include <iostream>
#include <fstream>
using namespace std;

int main(){
    for (int i = 0; i < 1000; i++){
        string oldName;
        if (i < 10) oldName = "primes.000" + to_string(i); 
        else if (i < 100) oldName = "primes.00" + to_string(i);
        else oldName = "primes.0" + to_string(i);

        string newName = "primes." + to_string(i);

        rename(oldName.c_str(), newName.c_str());
    }

    return 0;
}