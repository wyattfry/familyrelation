# Family Relation
This is a simple command line app that parses family relationships.
Example interaction:
```
Enter a family relationships (or 'help'): help
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Start with 'my' and write an expression using any of the following terms
mother, father, wife, husband, sister, brother, son, daughter
> "my father's mother's sister" returns "grand aunt"
To quit, enter "quit"
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Enter a family relationships (or 'help'): my son's wife
Your son's wife is your daughter in-law.

Enter a family relationships (or 'help'): my father's sister's daughter
Your father's sister's daughter is your cousin.

Enter a family relationships (or 'help'): my wife's mother's father's brother
Your wife's mother's father's brother is your grand uncle in-law.

Enter a family relationships (or 'help'): my husband's father's sister's son
Your husband's father's sister's son is your cousin in-law.
```
## Instructions
To compile, ``javac FamilyRelation.java``, to run ``java FamilyRelation``
