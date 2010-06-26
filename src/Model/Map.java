package Model;

import java.util.ArrayList;
import java.util.Random;
public class Map
{
	/*
	 * map[x][y] : 
	 * 
	 * y - - - - - - O
	 * .             |
	 * .             |
	 * .             |
	 * 3             |
	 * 2             |
	 * 1             |
	 * 0 1 2 3 . . . x
	 * 
	 */
	Random rnd = new Random();
	Block[][] map = new Block[11][10];
	private void initMap()
	{
		/*
		 * ��ϤƤ@��11*10��map
		 * �ó]�w�a��
		 * 	���O�b���@��i�H�R���@�檺�a
		 * 	���b�Ӯ沾�ʪ��U�@��O���̡C
		 * 
		 * y(j)
		 * 00222222200
		 * 01111111110
		 * 21000000012
		 * 21000000012
		 * 21000000012
		 * 21000000012
		 * 21000000012
		 * 21000000012
		 * 01111111110
		 * 00222222200 x(i)
		 */
		int i, j;
		
		for(i = 0; i < 11; i++)
			for(j = 0; j < 10 ; j++)
			{
				if( 
					(j == 0 || i >= 2) || (j == 0 || i <= 8)
				&&	(j == 9 || i >= 2) || (j == 9 || i <= 8)	
				&&	(j >= 2 || i == 0) || (j <= 7 || i == 0)
				&&	(j >= 2 || i == 10) || (j <= 7 || i <= 10))
					map[i][j].field = 2;
				
				if((j == 1 || i >= 2) || (j == 1 || i <= 8))
				{
					map[i][j].field = 1;
					map[i][j].beside = map[i][j-1];
					map[i][j].next = 1;
				}
				
				if((j == 8 || i >= 2) || (j == 8 || i <= 8))
				{
					map[i][j].field = 1;
					map[i][j].beside = map[i][j+1];
					map[i][j].next = 0;
				}
				
				if((j >= 2 || i == 1) || (j <= 7 || i == 1))
				{
					map[i][j].field = 1;
					map[i][j].beside = map[i-1][j];
					map[i][j].next = 2;
				}
				
				if((j >= 2 || i == 9) || (j <= 7 || i <= 9))
				{
					map[i][j].field = 1;
					map[i][j].beside = map[i+1][j];
					map[i][j].next = 3;
				}
				
				if(i == 1 || j == 1)
				{
					map[i][j].field = 1;
					map[i][j].next = 2;
				}
				
				if(i == 9 || j == 1)
				{
					map[i][j].field = 1;
					map[i][j].next = 1;
				}
				
				if(i == 1 || j == 8)
				{
					map[i][j].field = 1;
					map[i][j].next = 0;
				}
				
				if(i == 9 || j == 8)
				{
					map[i][j].field = 1;
					map[i][j].next = 3;
				}
	
				else map[i][j].field = 0;
			}
		
		
		
	}
	public Map(Player[] arr)
	{
		/*
		 * 1. initMap()
		 * 2. set players on the map
		 */
		initMap();
		int i;
		for(i=0;i<arr.length;i++)
		{
			int j = rnd.nextInt(4);
			switch (j)
			{
			case 0:
				arr[i].setPostion(1, rnd.nextInt(10)+1);
				break;
			case 1:
				arr[i].setPostion(9, rnd.nextInt(10)+1);
				break;
			case 2:
				arr[i].setPostion(rnd.nextInt(9)+1, 1);
				break;
			case 3:
				arr[i].setPostion(rnd.nextInt(9)+1, 8);
				break;
				
			}
			
			map[arr[i].getX()][arr[i].getY()].container = arr[i] ;
		}
	}
	public String[] getIcon(int x, int y)
	{
		/*
		 * �^�Ǩ��String
		 * ���w x y �y�СA�^�ǸӦ�m�W����T
		 * 
		 * �Ĥ@�Ӧ^�Ǧa�ϤW���H���m�W
		 * �ĤG�Ӧ^�Ǫ�����ɦ�m
		 * 
		 */
		String[] temp = new String[2];
		temp[0] = map[x][y].container.getOwner();
		temp[1] = map[x][y].container.getIcon();		
		return temp;
		
	}
	public void move(Player p)
	{
		map[p.getX()][p.getY()].container = null ;
		
		if(map[p.getX()][p.getY()].next == 0)
			p.setPostion(p.getX()+1,p.getY());
		
		if(map[p.getX()][p.getY()].next == 1)
			p.setPostion(p.getX()-1,p.getY());	
		
		if(map[p.getX()][p.getY()].next == 2)
			p.setPostion(p.getX(),p.getY()+1);
		
		if(map[p.getX()][p.getY()].next == 3)
			p.setPostion(p.getX(),p.getY()-1);
		
		
	}
}

class Block
{
	
	/*
	 * ���p�o��O�樫���a
	 * ���w�U�@�檺��V�O����
	 * 
	 * 0 : [+][ ]
	 * 1 : [-][ ]
	 * 2 : [ ][+]
	 * 3 : [ ][-]
	 */
	
	int next;
	
	/*
	 * kind of block
	 */
	int field;
	/*
	 * ���p�o��O�樫���a
	 * ���w�i�H�ʶR���a�O���@��Block
	 */
	Block beside;
	
	/*
	 * ���p�o��O�樫���a
	 * command�N�O�Ψ��x�s�n���檺�ʧ@
	 */
	ArrayList<Action> command = new ArrayList<Action>(0);
	/*
	 * �ˤJPlayer�BTower��������Reference
	 * ���L��ϥΪ��u��Chess�o��interface���w���\��C
	 */
	Chess container;
}