import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * SWEA 4012 : 요리사
 * 짝수개의 식재료를 두 개의 집단으로 나누었을 때 시너지의 합간의 차가 최소가 되도록 프로그래밍
 */
public class SWEA4012 {
	static int N;
	
	static int[][] S;
	static boolean[] select;
	
	static int answer;
	public static void main(String[] args) throws FileNotFoundException {
		// System.setIn(new FileInputStream("input.txt"));
		
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
			answer = Integer.MAX_VALUE;
			
			N = sc.nextInt();
			
			S = new int[N][N];
			for(int i = 0; i < N; i++)
				for(int j = 0; j < N; j++)
					S[i][j] = sc.nextInt();
			// 조합으로 식재료 만들기
			select = new boolean[N];
			solve(0, 0);
			
			// 결과 출력
			System.out.format("#%d %d\n", test_case, answer);
		}
		
		sc.close();
	}

	public static void solve(int cnt, int idx) {
		// A에 들어갈 식재료를 모두 선택했다면
		if(cnt == N / 2) {
			int sum = 0;
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					// true일 경우 A의 식재료
					if(select[i] && select[j])
						sum += S[i][j];
					// false일 경우 B의 식재료로 뺀다
					if(!select[i] && !select[j])
						sum -= S[i][j];
				}
			}
			// 앞에서 찾은 값과 지금 값을 비교하여 최소값을 갱신
			answer = Math.min(answer, Math.abs(sum));
			
			return;
		}
		
		for(int i = idx; i < N; i++) {
			// A식재료를 찾은 걸로 생각하여 true
			select[i] = true;
			solve(cnt + 1, i + 1);
			select[i] = false;
		}
	}
}
