def reverseOddCount(str):
    l1 = [j for j in range(len(str)) if(str.count(str[j])%2==0)]# [4,7]
    res = [None]*len(str)   
    for s in range(len(l1)):
        res.insert(l1[s],str[l1[s]])
    j = len(str)-1
    for i in range(len(str)):
        if not (i in l1):
            while(True):
                if(j in l1):
                    j-=1
                else:
                    break
            res.insert(j,str[i])
            j-=1    
    res = list(filter(None,res))
    return "".join(res)
