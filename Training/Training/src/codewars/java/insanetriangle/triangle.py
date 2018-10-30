import math;

triangleMatrice = {'R':{'R' : 'R',
                           'G' : 'B',
                           'B' : 'G' },
                      'B':{'R' : 'G',
                           'G' : 'R',
                           'B' : 'B' },
                      'G':{'R' : 'B',
                           'G' : 'G',
                           'B' : 'R' }};

pascalFractal = [2, 4, 10, 28, 82, 244, 730, 2188, 6562, 19684, 59050, 177148, 531442, 1594324, 4782970, 14348908, 43046722, 129140164, 387420490, 1162261468, 3486784402, 31381059610, 282429536482];
indexFractal = 1;
dictionnaire = {};

def triangle(row):
    global indexFractal;
    indexFractal = len(pascalFractal)-1;
    global dictionnaire;
    dictionnaire = {};
    soluce = getTriangleSolution(row, 0, len(row) - 1);
    return soluce;

def getTriangleSolution(chaine, debut, fin): 
    taille = fin - debut + 1 ;
    if taille == 1:
        return chaine[debut];
    taillePlusGrandTriangle = getTaillePlusGrandTriangle(taille);
    debut = getTriangleSolution(chaine, debut, debut + taille - taillePlusGrandTriangle );
    fin = getTriangleSolution(chaine, fin - taille + taillePlusGrandTriangle , fin);
    return triangleMatrice[debut][fin];

def getTaillePlusGrandTriangle(chaineTaille):
    global dictionnaire;
    if chaineTaille in dictionnaire :
        return dictionnaire[chaineTaille];
    global indexFractal;
    while pascalFractal[indexFractal] > chaineTaille:
        indexFractal = indexFractal-1;
    dictionnaire[chaineTaille] = pascalFractal[indexFractal];
    return pascalFractal[indexFractal];
