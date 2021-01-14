# FirstName LastName
# StudentID 

# Don't change the variable name 'sequence'
# You can change the right-hand-side (DNA sequence) as you wish 
# to test your program
sequence = "ATTACGCGCAGCAG"

base_count = [0, 0, 0, 0]

for b in sequence:
    if b == 'A':
        base_count[0] += 1
    elif b == 'T':
        base_count[1] += 1
    elif b == 'C':
        base_count[2] += 1
    elif b == 'G':
        base_count[3] += 1
    else:
        print("Invalid base")
print('A: {}, T: {}, C: {}, G: {}'.format(base_count[0],base_count[1],base_count[2],base_count[3]))