import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 7699 : 수지의 수지 맞는 여행(D4)
 * 같은 알파벳을 한번씩만 방문하고 이동할 수 있는 최대 거리 구하기
 */
public class SWEA7699 {
	static int R;
	static int C;

	static char[][] map;

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	static int answer;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			answer = 1;

			StringTokenizer st = new StringTokenizer(br.readLine());

			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());

			map = new char[R][C];
			for (int i = 0; i < R; i++)
				map[i] = br.readLine().toCharArray();

			travel(0, 0, 1, (1 << (map[0][0] - 'A')));

			System.out.format("#%d %d\n", tc, answer);
		}

		br.close();
	}

	public static void travel(int row, int col, int cnt, int visited) {
//		answer가 26이라면 최대로 이동한 것이므로 무조건 종료
		if (answer == 26)
			return;
//		4방향 탐색을 진행
		for (int i = 0; i < 4; i++) {
			int nr = row + dr[i];
			int nc = col + dc[i];
			
			if(nr < 0 || nc < 0 || nr >= R || nc >= C)
				continue;
//			이미 방문한 곳이라면 최대값 갱신 후 continue
			if((visited & (1 << (map[nr][nc] - 'A'))) > 0) {
				answer = Math.max(cnt, answer);
				continue;
			}
//			방문 하지 않았을 경우 방문 및 방문 체크
			travel(nr, nc, cnt + 1, (visited | (1 << (map[nr][nc] - 'A'))));
		}
	}
}
