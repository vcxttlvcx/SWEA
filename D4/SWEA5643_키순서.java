import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 5643 : 키 순서
 */
public class SWEA5643_키순서 {
	private static int N;
	private static int M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			M = Integer.parseInt(br.readLine());

			boolean[][] small = new boolean[N + 1][N + 1];
			boolean[][] tall = new boolean[N + 1][N + 1];

			for (int i = 0; i < M; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				tall[b][a] = true;
				small[a][b] = true;
			}
			boolean[][] dp = new boolean[N + 1][N + 1];
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					dp[i][j] = tall[i][j];
					dp[i][j] = small[i][j];
				}
				dp[i][i] = true;
			}

			for (int k = 1; k <= N; k++) {
				for (int i = 1; i <= N; i++) {
					for (int j = 1; j <= N; j++) {
						if (!small[i][j])
							small[i][j] = small[i][k] && small[k][j];
						if (!tall[i][j])
							tall[i][j] = tall[i][k] && tall[k][j];
					}
				}
			}

			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					dp[i][j] = small[i][j] || tall[i][j];
				}
				dp[i][i] = true;
			}

			int ans = 0;
			loop: for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					if (!dp[i][j])
						continue loop;
				}
				ans++;
			}

			System.out.println("#" + tc + " " + ans);
		}

		br.close();
	}
}
