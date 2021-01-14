# FirstName LastName
# StudentID

# Don't change the variable name 'mRNA'
# You can change the right-hand-side (DNA sequence) as you wish 
# to test your program
mRNA = "AUUACGC"

found_start = False
found_stop = False
index_of_start
index_of_end
for i in range(len(mRNA)):
    if mRNA[i:i+3] == "AUG" and not(found_start):
        found_start = True
        index_of_start = i
for j in range(index_of_start, len(mRNA)):
    if mRNA[j:j+3] == "UAG" or mRNA[j:j+3] == "UGA" or mRNA[j:j+3] == "UAA" and not(found_stop):
        found_stop = True
        index_of_end = j
        
num_codons = int((index_of_end - index_of_start)/3)
print(num_codons)