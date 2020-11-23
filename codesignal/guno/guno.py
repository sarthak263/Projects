def guno(playercount, moves):
    i = 0
    j = len(moves)
    player = 0
    l1 = [0]*playercount
    dic1 = {"c" :0, 
            "s1":20,
            "s2":20,
            "s3":20,
            "s4":50,
            "s5":50}
    #sum_cols = [sum(x) for x in zip(*l1)]
    flag = 0
    while(i < j):
        if(flag % 2 == 1):
            
            player = (player % (playercount)) - 1
            if(player == 0):
                player == 4
        else:
            player = (player % (playercount)) + 1
            
            
        if(moves[i] == "c"):
            l1[player-1] = l1[player-1] + 0
            
        elif(moves[i] == "s1"):
            l1[player-1] = l1[player-1] + dic1["s1"]
            if(flag % 2 == 1):
                player = player - 1
            else:
                player = player + 1
            
            
        elif(moves[i] == "s2"):
            l1[player-1] = l1[player-1] + dic1["s2"]
            
            
        elif(moves[i] == "s3"):
            l1[player-1] = l1[player-1] + dic1["s3"]
            flag = flag + 1
            
        elif(moves[i] == "s4"):
            l1[player-1] = l1[player-1] + dic1["s4"]
            
        else:
            l1[player-1] = l1[player-1] + dic1["s5"]
            
        i+=1
        print(l1)                 
        
    return l1
                     
                                
                                
                                
                                
                                
