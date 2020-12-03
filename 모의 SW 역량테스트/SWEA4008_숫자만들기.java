import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 4008 : [모의 SW 역량테스트] 숫자 만들기
 * 
 * 선표는 게임을 통해 사칙 연산을 공부하고 있다.
 * N개의 숫자가 적혀 있는 게임 판이 있고, +, -, x, / 의 연산자 카드를 숫자 사이에 끼워 넣어 다양한 결과 값을 구해보기로 했다.
 * 수식을 계산할 때 연산자의 우선 순위는 고려하지 않고 왼쪽에서 오른쪽으로 차례대로 계산한다.
 * 예를 들어 1, 2, 3 이 적힌 게임 판에 +와 x를 넣어 1 + 2 * 3을 만들면 1 + 2를 먼저 계산하고 그 뒤에 * 를 계산한다.
 * 즉 1+2*3의 결과는 9이다.
 * 주어진 연산자 카드를 사용하여 수식을 계산했을 때 그 결과가 최대가 되는 수식과 최소가 되는 수식을 찾고, 두 값의 차이를 출력하시오.
 */
public class SWEA4008_숫자만들기 {

	private static int N;
	private static int[] opNum;
	private static int[] nums;
	private static int max;
	private static int min;
	private static int[] select;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());	// 숫자의 개수
			// 연사자 카드의 개수
			// 0 : +, 1 : -, 2 : *, 3 : /
			opNum = new int[4];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i = 0; i < 4; i++)
				opNum[i] = Integer.parseInt(st.nextToken());
			// 연산에 사용될 숫자
			nums = new int[N];
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++)
				nums[i] = Integer.parseInt(st.nextToken());
			
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			// 선택한 연산자 정보를 저장할 배열
			select = new int[N - 1];
			// 연산자 선택하여 최대값 최소값 찾기
			solve(0);
			
			System.out.println("#" + tc + " " + (max - min));
		}
		
		br.close();
	}

	private static void solve(int idx) {
		// 선택한 연산자의 개수가 N - 1과 같으면 기저 조건
		if(idx == N - 1) {
			// 연산자를 모두 선택했다면 수식을 계산한다
			int num = nums[0];
			for(int i = 1; i < N; i++) {
				switch (select[i - 1]) {
				case 0:	// +
					num += nums[i];
					break;
				case 1:	// -
					num -= nums[i];
					break;
				case 2:	// *
					num *= nums[i];
					break;
				case 3:	// /
					num /= nums[i];
					break;
				}
			}
			max = Math.max(max, num);
			min = Math.min(min, num);
			
			return;
		}
		// 어떤 연산자를 넣을 것인지 결정
		for(int i = 0; i < 4; i++) {
			if(opNum[i] == 0)
				continue;
			// 연산자 카드의 개수를 줄인 후 선택하여 다음 카드 선택으로 이동
			opNum[i]--;
			select[idx] = i;
			solve(idx + 1);
			// 연산자 카드의 개수 원상복구
			opNum[i]++;
		}
	}

}
