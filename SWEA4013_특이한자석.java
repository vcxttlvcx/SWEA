import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 4013 : [모의 SW 역량테스트] 특이한 자석
 */
public class SWEA4013_특이한자석 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int K = Integer.parseInt(br.readLine());

			int[][] magnet = new int[4][8];
			int[] redIdx = new int[4];
			for (int i = 0; i < 4; i++) {
				String input = br.readLine();
				for (int j = 0; j < 8; j++)
					magnet[i][j] = input.charAt(j * 2) - '0';
			}

			for (int i = 0; i < K; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int idx = Integer.parseInt(st.nextToken()) - 1; // 0번 자석부터 시작하기 위해 -1을 해줌
				int dir = Integer.parseInt(st.nextToken()) * -1; // index 방향으로 하려면 -1을 해줘야 됨

				int[] turn = new int[4];
				turn[idx] = dir;
				// 오른쪽 자석 돌리는지 확인하여 돌리기
				int now = idx;
				int tDir = dir;
				for (int j = now + 1; j < 4; j++) {
					if (magnet[now][((redIdx[now] + 2) % 8)] == magnet[j][((redIdx[j] + 6) % 8)])
						break;
					tDir *= -1;
					turn[j] = tDir;
					now = j;
				}
				// 왼쪽 자석 돌리는지 확인하여 돌리기
				now = idx;
				tDir = dir;
				for (int j = now - 1; j >= 0; j--) {
					if (magnet[now][((redIdx[now] + 6) % 8)] == magnet[j][((redIdx[j] + 2) % 8)])
						break;
					tDir *= -1;
					turn[j] = tDir;
					now = j;
				}
				// 자석 돌리기
				for (int j = 0; j < 4; j++) {
					if (turn[j] == 0)
						continue;

					redIdx[j] += turn[j];
					if (redIdx[j] < 0)
						redIdx[j] = 7;
					else
						redIdx[j] %= 8;
				}

			}
			// 2의 배수 관리를 위한 변수와 결과 저장을 위한 변수 선언
			int binary = 1;
			int result = 0;
			// 결과값 계산
			for (int i = 0; i < 4; i++) {
				result += magnet[i][redIdx[i]] * binary;
				binary *= 2;
			}

			StringBuilder ans = new StringBuilder();
			ans.append("#").append(tc).append(" ").append(result);
			System.out.println(ans.toString());
		} // end of for(tc)

		br.close();
	}

}
