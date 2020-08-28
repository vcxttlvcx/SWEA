import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3234 : 준환이의 양팔저울(D4)
 */
public class SWEA3234 {
	static int N;
	
	static int[] weight;
	
	static int answer;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			answer = 0;
			
			N = Integer.parseInt(br.readLine());
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			// 추 각각의 무게를 입력 받고 전체 추의 무게를 구해둔다
			weight = new int[N];
			int totalWeight = 0;
			for(int i = 0; i < N; i++) {
				weight[i] = Integer.parseInt(st.nextToken());
				totalWeight += weight[i];
			}
			
			solve(0, 0, 0, totalWeight, 0);
			// 결과 출력
			System.out.format("#%d %d\n", tc, answer);
		}
		br.close();
	}

	public static void solve(int cnt, int rightSum, int leftSum, int remain, int visited) {
		// 남은 모든 추를 오른쪽에 올려도 왼쪽이 더 무거우면 더 진행할 필요 없음
		if(leftSum >= rightSum + remain) {
			// 남은 추를 왼쪽에 올릴 것인지 오른쪽에 올릴 것인지의 경우의 수 2^(N - cnt)
			int caseNum = 1 << (N - cnt);
			// 남은 추를 올리는 순서를 정하기 (N - cnt)!
			for(int i = 1; i <= N - cnt; i++)
				caseNum *= i;
			
			answer += caseNum;
			return;
		}
		// 추를 다 사용했다면
		if(cnt == N) {
			answer++;
			return;
		}
		// 순열 행태로 추를 올려 나간다
		for(int i = 0; i < N; i++) {
			if((visited & (1 << i)) > 0)
				continue;
			// 왼쪽에 먼저 올리기
			solve(cnt + 1, rightSum, leftSum + weight[i], remain - weight[i], visited | (1 << i));
			// 추를 오른쪽에 올릴 시 오른쪽이 더 무거워 진다면 올리지 않는다
			if(rightSum + weight[i] > leftSum)
				continue;
			// 오른족에 추 올리기
			solve(cnt + 1, rightSum + weight[i], leftSum, remain - weight[i], visited | (1 << i));
		}
	}
}
