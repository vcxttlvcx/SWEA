import java.util.Scanner;

/**
 * SWEA 1247 : 최적 경로 (D5)
 * 김대리는 회사에서 출발하여 N명의 고객을 방문하고 집에 돌아가려한다 회사와 집의 위치, 그리고 각 고객의
 * 위치는 이차원 정수 좌표(x, y)로 주어지고 두 위치 (x1, y1)와 (x2, y2) 사이의 거리는 |x1 - x2| + |y1 -
 * y2| 이다. 회사에서 출발하여 모든 고객을 방문하고 집으로 돌아오는 경로 중 가장 짧은 것을 찾으시오
 */
public class SWEA1247 {
	static int N = 0;
	
	static Point company;
	static Point home;
	static Point[] customer;
	static boolean[] isVisited;
	
	static int[] select;
	
	static int ans = 0;

	public static void main(String[] args) {
		// System.setIn(new FileInputStream("res/input.txt"));
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();
		
		for (int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			
			ans = Integer.MAX_VALUE;
			
			company = new Point(sc.nextInt(), sc.nextInt());
			home = new Point(sc.nextInt(), sc.nextInt());
			
			select = new int[N];
			
			isVisited = new boolean[N];
			customer = new Point[N];
			for(int i = 0; i < N; i++)
				customer[i] = new Point(sc.nextInt(), sc.nextInt());
			
			solve(0, 0, company);
			
			System.out.format("#%d %d\n", test_case, ans);
		}
		
		sc.close();
	}
//	두 좌표간의 거리를 계산하여 반환
	static int calcDistance(Point p1, Point p2) {
		return (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y));
	}
//	dfs 형태로 탐색하며 최소 경로를 탐색한다
	static void solve(int cnt, int distance, Point before) {
//		이미 찾은 최소 경로 보다 커질 경우 종료
		if(distance >= ans)
			return;
//		집을 모두 방문 했을 경우 마지막 으로 집으로 돌아가는 거리를 더해 주고 최종 확인
		if(cnt == N) {
			distance += calcDistance(before, home);
			ans = Math.min(distance, ans);
			return;
		}
//		순열을 만들어 가며 최소 경로를 탐색한다
		for(int i = 0; i < N; i++) {
			if(isVisited[i])
				continue;
			
			isVisited[i] = true;
			select[cnt] = i;
			solve(cnt + 1, distance + calcDistance(before, customer[i]), customer[i]);
			isVisited[i] = false;
		}
	}

	static class Point {
		int x;
		int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
