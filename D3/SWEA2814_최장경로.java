import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2814 : 최장 경로
 * N개의 정점과 M개의 간선으로 구성된 가중치가 없는 무방향 그래프에서의 최장 경로의 길이를 계산하자.
 * 정점의 번호는 1번부터 N번까지 순서대로 부여되어 있다.
 * 경로에는 같은 정점의 번호가 2번 이상 등장할 수 없으며, 경로 상의 인접한 점들 사이에는 반드시 두 정점을 연결하는 간선이 존재해야 한다.
 * 경로의 길이는 경로 상에 등장하는 정점의 개수를 나타낸다.
 */
public class SWEA2814_최장경로 {
	private static int N;
	private static int M;
	
	private static int[][] edge;
	private static boolean[] visited;
	
	private static int ans;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			// 정점의 개수와 간선의 개수 입력
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			// 간선 정보 입력 받기
			edge = new int[N + 1][N + 1];
			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				edge[x][y] = 1;
				edge[y][x] = 1;
			}
			// 방문 체크를 위한 배열 초기화
			visited = new boolean[N + 1];
			// 최장 경로를 저장할 변수 초기화, 최대 값을 찾아야하므로 0으로 초기화
			ans = 0;
			// 연결이 끊어져 있는 경우를 위해 모든 정점에 대해서 dfs 탐색 실시
			for(int i = 1; i <= N; i++) {
				if(ans == N) {
					break;
				}
				visited[i] = true;
				dfs(i, 1);
				visited[i] = false;
			}
			// 결과 출력
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(ans);
			System.out.println(sb.toString());
		}
		
		br.close();
	}
	/**
	 * 최장 경로를 탐색하기 위한 dfs 메소드
	 * @param n : 현재 정점
	 * @param cnt : 현재 경로의 길이
	 */
	private static void dfs(int n, int cnt) {
		if(cnt == N || !canGoing(n)) {
			ans = cnt > ans ? cnt : ans;
			return;
		}
		
		for(int i = 1; i <= N; i++) {
			if(edge[n][i] != 1 || visited[i])
				continue;
			
			visited[i] = true;
			dfs(i, cnt + 1);
			visited[i] = false;
		}
	}
	/**
	 * 현재 위치에서 더 갈 수 있는 정점이 있는지 확인
	 * @param n : 현재 정점의 위치
	 * @return : 갈 수 있다면 true, 없다면 false
	 */
	static boolean canGoing(int n) {
		for(int i = 1; i <= N; i++) {
			if(edge[n][i] == 1 && !visited[i])
				return true;
		}
		
		return false;
	}
}
