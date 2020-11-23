# codesignal-guno

Guno, a variant of Uno, is a card game where players score points for each card discarded from their hand during the round. Action cards (Skip, Draw Two and Reverse) are worth 20 Points while Wild and Wild Draw Four cards are worth 50 Points. All other coloured cards are worth 0. The only difference between Uno and Guno is how the scoring works - in Uno, your score is the sum of cards in your hand at the end of the game, while in Guno, your score increases as you discard each card.
You and your friends have just finished a game of Guno and you are all unable to agree on everyone's score. Hence, you have decided to write an algorithm to calculate each player's score.
Given an integer playercount representing the number of players, and an array moves representing each of the cards that were played, in the order in which they were played, your task is to return an array containing each player's score.
The moves array contains the following types of cards:

c - A coloured card (colour and number irrelevant); 0 points awarded

s1 - A skip; 20 points awarded, and the next player skips their turn

s2 - A draw two; 20 points awarded

s3 - A reverse; 20 points awarded, and the turn order is reversed

s4 - A wild draw four; 50 points awarded

s5 - A wild; 50 points awarded


For playercount = 4 and moves = ["c", "s5", "c", "s4", "s1", "c", "s3", "s2"], the output should be guno(playercount, moves) = [20, 50, 20, 70].
The game would play out like this:


Player 1 will place a coloured card. They will receive no points and their turn will end
Player 2 will place a Wild card. They will receive 50 points and their turn will end
Player 3 will place a coloured card. They will receive no points and their turn will end
Player 4 will place a Wild Draw Four card. They will receive 50 points and their turn will end
Player 1 will place a Skip card. They will receive 20 points and their turn will end skipping player 2
Player 3 will place a coloured card. They will receive no points and their turn will end
Player 4 will place a Reverse card. They will receive 20 points and their turn will end reversing the direction for play until another reverse card is played
Player 3 will place a Draw Two card. They will receive 20 points and their turn will end
Tallying up each column, the final scores are [20, 50, 20, 70].
