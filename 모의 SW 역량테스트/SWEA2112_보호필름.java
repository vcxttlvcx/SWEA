import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2112 : [모의 SW 역량테스트]보호 필름
 */
public class SWEA2112_보호필름 {
	private static int D;
	private static int W;
	private static int K;
	private static int[][] film;
	private static int min;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			D = Integer.parseInt(st.nextToken());	// 보호 필름의 두께
			W = Integer.parseInt(st.nextToken());	// 가로 크기
			K = Integer.parseInt(st.nextToken());	// 합격 기준
			min = Integer.MAX_VALUE;	// 약품 투여 최소 회수
			// 보호 필름 정보를 저장할 배열 초기화
			film = new int[D][W];
			// 보호 필름 정보 입력
			for(int i = 0; i < D; i++) {
				String input = br.readLine();
				for(int j = 0; j < W; j++)
					film[i][j] = input.charAt(j * 2) - '0';
			}
			// 첫줄에 투입 했을 때와 안했을 때를 구분하여 검사하면 모든 경우의 수를 찾을 수 있다
			dfs(0, 0);
			// 결과 출력
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(test_case).append(" ").append(min);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 약품을 투입하여 최소 투입 회수를 찾는다
	 * @param n : 현재 약품을 투입할지 말지를 결정할 행
	 * @param cnt : 약품을 투입한 회수
	 */
	private static void dfs(int n, int cnt) {
		if(cnt >= min)
			return;
		if(n >= D) {
			if(performanceTest())
				min = cnt < min ? cnt : min;
			
			return;
		}
		int[] temp = new int[W];
		// A 약품을 투입한 경우
		for(int i = 0; i < W; i++) {
			temp[i] = film[n][i];
			film[n][i] = 0;
		}
		dfs(n + 1, cnt + 1);
		// B 약품을 투입한 경우
		for(int i = 0; i < W; i++) {
			film[n][i] = 1;
		}
		dfs(n + 1, cnt + 1);
		// 약품을 투입하지 않는 경우
		for(int i = 0; i < W; i++) {
			film[n][i] = temp[i];
		}
		dfs(n + 1, cnt);
	}

	private static boolean performanceTest() {
		int totalCnt = 0;
		for(int col = 0; col < W; col++) {
			if(totalCnt != col)
				return false;
			
			int before = film[0][col];
			int cnt = 1;
			for(int row = 1; row < D; row++) {
				if(cnt >= K) {
					break;
				}
				// 현재 필름이 위의 필름과 같지 않다면
				if(film[row][col] != before) {
					cnt = 1;
					before = film[row][col];
					continue;
				}
				cnt++;
			}
			if(cnt >= K)
				totalCnt++;
		}
		if(totalCnt == W)
			return true;
		return false;
	}

}
