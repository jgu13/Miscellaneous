import csv

def convert_list_to_string(list_of_str, seperator):
    res_str = seperator.join(list_of_str)
    return res_str
        
#open file as append mode
with open('Report.csv', 'a', newline='') as csvfile:
    writer = csv.writer(csvfile, dialect = 'excel')
    header = ['Name', 'Link', 'Year', 'website', 'Description', 'Link to instruction', 'factors', 'databases', 'list of correspounding papers']
    writer.writerow(header)
    
cont = True
while cont:
    Name = input('Name of the tool: ')
    Link = input('link to the paper: ')
    Year = input('In what year was the paper published: ')
    website = input('Website of the tool: ')
    descrption = input('Brief description of the tool: ')
    instruction_link = input('Link to instruction of the tool: ')
    Is_comp = input("Is the tool provided by a company? y for Yes, n for No")
    if Is_comp == 'y':
        Name = '\u0332'.join(Name)
        print(Name)
    #while loop to get factors, databases and links of correspounding papers 
    factors = []
    databases = []
    list_of_corr_papers = []
    #while loop to get factors
    factor_loop = True
    while factor_loop:
        factors_str = (input('Please enter factors/options customized in the tool: '))
        factors_str = factors_str.split(",")
        for factor in factors_str:
            factors.append(factor)
        ans = input("Do you want to continue adding factors? Enter'y' for Yes, 'n' for No")
        if ans == 'n':
            factor_loop = False
        else:
            factor_loop = True
    
    #while loop to get databases
    data_base = []
    data_loop = True
    while data_loop:
        data_str = (input('Please enter databases used in the tool: ex: database1, database2, ...'))
        data_str = data_str.split(",")
        for data in data_str:
            data_base.append(data)
        ans = input("Do you want to continue adding database? Enter'y' for Yes, 'n' for No")
        if ans == 'n':
            data_loop = False
        else:
            data_loop = True
    
    #while loop to get correspounding papers
    papers = []
    paper_loop = True
    while paper_loop:
        paper_str = (input('Please enter correspounding papers: '))
        paper_str = paper_str.split(",")
        for paper in paper_str:
            papers.append(paper)
        ans = input("Do you want to continue adding paper? Enter'y' for Yes, 'n' for No")
        if ans == 'n':
            paper_loop = False
        else:
            paper_loop = True
    
    with open('Report.csv', 'a', encoding="utf-8", newline='') as csvfile:
        writer = csv.writer(csvfile, dialect = 'excel')
        
        factors = convert_list_to_string(factors, ', ')
        data_bases = convert_list_to_string(data_base, ', ')
        papers = convert_list_to_string(papers, ', ')
        
        writer.writerow([Name, Link, Year, website, descrption, instruction_link, factors, data_bases, papers])
    
    ans = input('Do you want to enter info of another tool? y for Yes, n for No')
    if ans == 'n':
        cont = False
    else:
        cont = True
        
print('You have finished entering all the tools!')
         
            



