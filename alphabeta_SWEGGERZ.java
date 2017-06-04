/*
 * ECS 170 Programming Assignment 2
 * Date: March 1, 2017
 * 
 * Team Name: SWEGGERZ
 * 
 * Programmers:
 *   Susie Chac (912004424)
 *   Melody Chang (912110826)
*/
public class alphabeta_SWEGGERZ extends AIModule
{
	private int player1;
	private int player2;

	// Array containing all possible 69 combinations of four-in-a-rows
	private int[][][] winningFour = 
	{
		// vertical wins
		{ {0, 0}, {0, 1}, {0, 2}, {0, 3} },
		{ {0, 1}, {0, 2}, {0, 3}, {0, 4} },
		{ {0, 2}, {0, 3}, {0, 4}, {0, 5} },

		{ {1, 0}, {1, 1}, {1, 2}, {1, 3} },
		{ {1, 1}, {1, 2}, {1, 3}, {1, 4} },
		{ {1, 2}, {1, 3}, {1, 4}, {1, 5} },

		{ {2, 0}, {2, 1}, {2, 2}, {2, 3} },
		{ {2, 1}, {2, 2}, {2, 3}, {2, 4} },
		{ {2, 2}, {2, 3}, {2, 4}, {2, 5} },

		{ {3, 0}, {3, 1}, {3, 2}, {3, 3} },
		{ {3, 1}, {3, 2}, {3, 3}, {3, 4} },
		{ {3, 2}, {3, 3}, {3, 4}, {3, 5} },

		{ {4, 0}, {4, 1}, {4, 2}, {4, 3} },
		{ {4, 1}, {4, 2}, {4, 3}, {4, 4} },
		{ {4, 2}, {4, 3}, {4, 4}, {4, 5} },

		{ {5, 0}, {5, 1}, {5, 2}, {5, 3} },
		{ {5, 1}, {5, 2}, {5, 3}, {5, 4} },
		{ {5, 2}, {5, 3}, {5, 4}, {5, 5} },

		{ {6, 0}, {6, 1}, {6, 2}, {6, 3} },
		{ {6, 1}, {6, 2}, {6, 3}, {6, 4} },
		{ {6, 2}, {6, 3}, {6, 4}, {6, 5} },		

		// horizontal wins
		{ {0, 0}, {1, 0}, {2, 0}, {3, 0} },
		{ {1, 0}, {2, 0}, {3, 0}, {4, 0} },
		{ {2, 0}, {3, 0}, {4, 0}, {5, 0} },
		{ {3, 0}, {4, 0}, {5, 0}, {6, 0} },

		{ {0, 1}, {1, 1}, {2, 1}, {3, 1} },
		{ {1, 1}, {2, 1}, {3, 1}, {4, 1} },
		{ {2, 1}, {3, 1}, {4, 1}, {5, 1} },
		{ {3, 1}, {4, 1}, {5, 1}, {6, 1} },

		{ {0, 2}, {1, 2}, {2, 2}, {3, 2} },
		{ {1, 2}, {2, 2}, {3, 2}, {4, 2} },
		{ {2, 2}, {3, 2}, {4, 2}, {5, 2} },
		{ {3, 2}, {4, 2}, {5, 2}, {6, 2} },

		{ {0, 3}, {1, 3}, {2, 3}, {3, 3} },
		{ {1, 3}, {2, 3}, {3, 3}, {4, 3} },
		{ {2, 3}, {3, 3}, {4, 3}, {5, 3} },
		{ {3, 3}, {4, 3}, {5, 3}, {6, 3} },

		{ {0, 4}, {1, 4}, {2, 4}, {3, 4} },
		{ {1, 4}, {2, 4}, {3, 4}, {4, 4} },
		{ {2, 4}, {3, 4}, {4, 4}, {5, 4} },
		{ {3, 4}, {4, 4}, {5, 4}, {6, 4} },

		{ {0, 5}, {1, 5}, {2, 5}, {3, 5} },
		{ {1, 5}, {2, 5}, {3, 5}, {4, 5} },
		{ {2, 5}, {3, 5}, {4, 5}, {5, 5} },
		{ {3, 5}, {4, 5}, {5, 5}, {6, 5} },

		// diagonal wins up from left
		{ {0, 0}, {1, 1}, {2, 2}, {3, 3} },
		{ {1, 0}, {2, 1}, {3, 2}, {4, 3} },
		{ {2, 0}, {3, 1}, {4, 2}, {5, 3} },
		{ {3, 0}, {4, 1}, {5, 2}, {6, 3} },

		{ {0, 1}, {1, 2}, {2, 3}, {3, 4} },
		{ {1, 1}, {2, 2}, {3, 3}, {4, 4} },
		{ {2, 1}, {3, 2}, {4, 3}, {5, 4} },
		{ {3, 1}, {4, 2}, {5, 3}, {6, 4} },

		{ {0, 2}, {1, 3}, {2, 4}, {3, 5} },
		{ {1, 2}, {2, 3}, {3, 4}, {4, 5} },
		{ {2, 2}, {3, 3}, {4, 4}, {5, 5} },
		{ {3, 2}, {4, 3}, {5, 4}, {6, 5} },

		// diagonal wins down from left
		{ {0, 5}, {1, 4}, {2, 3}, {3, 2} },
		{ {1, 5}, {2, 4}, {3, 3}, {4, 2} },
		{ {2, 5}, {3, 4}, {4, 3}, {5, 2} },
		{ {3, 5}, {4, 4}, {5, 3}, {6, 2} },

		{ {0, 4}, {1, 3}, {2, 2}, {3, 1} },
		{ {1, 4}, {2, 3}, {3, 2}, {4, 1} },
		{ {2, 4}, {3, 3}, {4, 2}, {5, 1} },
		{ {3, 4}, {4, 3}, {5, 2}, {6, 1} },

		{ {0, 3}, {1, 2}, {2, 1}, {3, 0} },
		{ {1, 3}, {2, 2}, {3, 1}, {4, 0} },
		{ {2, 3}, {3, 2}, {4, 1}, {5, 0} },
		{ {3, 3}, {4, 2}, {5, 1}, {6, 0} },
	};

	private int evaluationFunction(final GameStateModule game)
	{
		if(game.isGameOver())
		{
			if(game.getWinner() == this.player1)
			{
				// player 1 wins!
				return Integer.MAX_VALUE;
			}
			else if(game.getWinner() == this.player2)
			{
				// player 2 wins!
				return Integer.MIN_VALUE;
			} 
			else
			{
				// it's a tie!
				return 0;
			}
		}

		int currPlayer = game.getActivePlayer();
		int score = 0;
		int p1Coins;
		int p2Coins;
		int x;// = 0; // x-coord of loc
		int y;// = 0; // y-coord of loc

		for(int[][] oneWin : winningFour)
		{
			x = 0; // x-coord of loc
			y = 0; // y-coord of loc

			// reset num coins for each possible win combo
			p1Coins = 0;
			p2Coins = 0;

			// OneLoc is one of the four positions in OneWin
			for(int[] oneLoc: oneWin)
			{
				// get the player at one location in a winning combo of 4
				int player = game.getAt(oneLoc[0], oneLoc[1]);

				// check which player is at the loc and increment their coin num
				if(player == this.player1)
				{
					// player1 has p1Coins # coins in a winning position
					p1Coins++;
				}
				else if(player == this.player2)
				{
					// player2 has p2Coins # coins in a winning position
					p2Coins++;
				}
				else
				{
					// currently no player has a coin at this loc
					// critical space is empty
					// set x and y coordi equal to critical space loc
					x = oneLoc[0];
					y = oneLoc[1];
				}
			}

			// check if at least one player has coins in a winning position
			if(p1Coins == 0 || p2Coins == 0)
			{
				// check if player1 has 3 coins in winning positions
				if(p1Coins == 3)
				{
					// if y is not bottom-most row
					// and space below critical space is empty
					if(y > 0 && game.getAt(x, y-1) == 0)
					{
						if(this.player1 == 1)
						{
							// player 1 gets odd rows if player 1 plays first
							if(y % 2 == 0)
							{
								score += 10;
							}
						} 
						else
						{
							// player 1 gets even rows if player 1 plays second
							if(y % 2 == 1)
							{
								score += 10;
							}
						}
					}
					else if(currPlayer == this.player1)
					{
						return Integer.MAX_VALUE;
					}
				}
				// check if p2 has 3 coins in a winning position
				else if(p2Coins == 3)
				{
					// if y is not bottom-most row
					// and space below critical space is empty
					if(y > 0 && game.getAt(x, y-1) == 0)
					{
						if(this.player2 == 1)
						{
							// player 2 takes odd rows if player 2 plays first
							if(y % 2 == 0)
							{
								score -= 10;
							}
						} 
						else
						{
							// player 2 takes even rows if player 2 plays second
							if(y % 2 == 1)
							{
								score -= 10;
							}
						}
					}
					else if(currPlayer == this.player2)
					{
						return Integer.MIN_VALUE;
					}
				}	
				// score > 0 means that player 1 wins!
				// score < 0 means that player 2 wins!
				// score == 0 means that it's a tie!
				score += (p1Coins - p2Coins);
			}
		}
		return score;
	}

	private int alphaBeta(final GameStateModule game, int depth, int alpha, int beta, int curr, boolean maximizingPlayer)
	{
		if(game.isGameOver() || terminate || depth == 0)
		{
			// run eval function if end of the game
			// or reached the bottom of tree
			return evaluationFunction(game);
		}

		if(maximizingPlayer)
		{
			// alpha represents maximum score that the maximizing player is assured of
			int v = Integer.MIN_VALUE;
			for(int i = 0; i < game.getWidth(); i++)
			{
				if(game.canMakeMove(i))
				{
					game.makeMove(i);
					v = Math.max(v, alphaBeta(game, depth-1, alpha, beta, i, !maximizingPlayer));
					alpha = Math.max(alpha, v);
					game.unMakeMove();
					if(beta <= alpha)
					{
						break; // Beta cutoff
					}
				}
			}
			return alpha;
		}
		else
		{
			// beta represents minimum score that the minimizing player is assured of
			int v = Integer.MAX_VALUE;
			for(int i = 0; i < game.getWidth(); i++)
			{
				if(game.canMakeMove(i))
				{
					game.makeMove(i);
					v = Math.min(v, alphaBeta(game, depth-1, alpha, beta, i, !maximizingPlayer));
					beta = Math.min(beta, v);
					game.unMakeMove();
					if(beta <= alpha)
					{
						break; // Alpha cutoff
					}
				}
			}
			return beta;
		}
	}

	@Override
	public void getNextMove(final GameStateModule game)
	{
		// when the number of coins dropped is 0 or 1
		if(game.getCoins() < 2)
		{
			// drop coin into middle column
			chosenMove = 3;
			return;
		}
		// set the order of player 1 and player 2
		this.player1 = game.getActivePlayer();
		if(this.player1 == 1)
		{
			this.player2 = 2;
		}
		else
		{
			this.player2 = 1;
		}

		// depth of tree (# of moves ahead from chosen move)
		int depth = 6;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;

		for(int i = 0; i < game.getWidth(); i++)
		{
			if(terminate)
			{
				break;
			}
			if(game.canMakeMove(i))
			{
				game.makeMove(i); // go down tree
				int v = alphaBeta(game, depth, alpha, beta, i, false);
				game.unMakeMove();
				if(v > alpha)
				{
					alpha = v;
					chosenMove = i;
				}
			}
		}
	}
}