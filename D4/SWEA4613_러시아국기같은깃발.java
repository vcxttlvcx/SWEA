import java.util.Scanner;

/**
 * SWEA 4613 : 러시아 국기 같은 깃발(D4)
 * 2016년은 삼성전자가 러시아 현지법인을 설립한지 20주년이 된 해이다. 이를 기념해서 당신은 러시아 국기를 만들기로 했다.
 * 먼저 창고에서 오래된 깃발을 꺼내왔다. 이 깃발은 N행 M열로 나뉘어 있고, 각 칸은 흰색, 파란색, 빨간색 중 하나로 칠해져 있다.
 * 당신은 몇 개의 칸에 있는 색을 다시 칠해서 이 깃발을 러시아 국기처럼 만들려고 한다. 다음의 조건을 만족해야 한다.
 * 위에서 몇 줄(한 줄 이상)은 모두 흰색으로 칠해져 있어야 한다.
 * 다음 몇 줄(한 줄 이상)은 모두 파란색으로 칠해져 있어야 한다.
 * 나머지 줄(한 줄 이상)은 모두 빨간색으로 칠해져 있어야 한다.
 * 이렇게 러시아 국기 같은 깃발을 만들기 위해서 새로 칠해야 하는 칸의 개수의 최솟값을 구하여라.
 */
public class SWEA4613_러시아국기같은깃발 {
	static int ans;
	
	static int N;
	static int M;
	
	static char[][] map;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		
		for(int tc = 1; tc <= T; tc++) {
			ans = Integer.MAX_VALUE;

			N = sc.nextInt();
			M = sc.nextInt();
			
			map = new char[N][M];
			int[][] dp = new int[N][3];
			
			for(int i = 0; i < N; i++) {
				String now = sc.next();
				
				for(int j = 0; j < M; j++) {
					map[i][j] = now.charAt(j);
					if(map[i][j] != 'W')
						dp[i][0]++;
					if(map[i][j] != 'B')
						dp[i][1]++;
					if(map[i][j] != 'R')
						dp[i][2]++;
				}
			}
			
			int wCnt = 0;
			for(int w = 0; w < N - 2; w++) {
				wCnt += dp[w][0];
				
				int bCnt = 0;
				for(int b = w + 1; b < N - 1; b++ ) {
					bCnt += dp[b][1];
					
					int rCnt = 0;
					for(int r = b + 1; r < N; r++) {
						rCnt += dp[r][2];
					}
					
					for(int r = b + 1; r < N; r++) {
						ans = Math.min(ans, wCnt + bCnt + rCnt);
						rCnt -= dp[r][2];
						bCnt += dp[r][1];
					}
				}
			}
			
			System.out.format("#%d %d\n", tc, ans);
		}
		
		sc.close();
	}

}
