import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * SWEA 4366 : 정식이의 은행업무(D4)
 * 삼성은행의 신입사원 정식이는 실수를 저질렀다.
 * 은행 업무가 마감되기 직전인 지금, 송금할 금액을 까먹고 말았다.
 * 하지만 다행스럽게도 정식이는 평소 금액을 2진수와 3진수의 두 가지 형태로 기억하고 다니며, 기억이 명확하지 않은 지금조차 2진수와 3진수 각각의 수에서 단 한 자리만을 잘못 기억하고 있다는 것만은 알고 있다.
 * 예를 들어 현재 기억이 2진수 1010과 3진수 212을 말해주고 있다면 이는 14의 2진수인 1110와 14의 3진수인 112를 잘못 기억한 것이라고 추측할 수 있다.
 * 정식이는 실수를 바로잡기 위해 당신에게 부탁을 하였다.
 * 정식이가 송금액을 추측하는 프로그램을 만들어주자.
 * (단, 2진수와 3진수의 값은 무조건 1자리씩 틀리다. 추측할 수 없는 경우는 주어지지 않는다.)
 */
public class SWEA4366_정식이의은행업무 {
	static String base2;
	static String base3;

	static long answer;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		// 테스트케이스 시작
		for (int tc = 1; tc <= T; tc++) {
			answer = 0;

			base2 = br.readLine();
			base3 = br.readLine();
			// 2진수에서 나올 수 있는 결과 값 저장
			ArrayList<Long> list = new ArrayList<Long>();
			// 2진수 입력 값을 한 자리씩 바꿔가며 나올 수 있는 모든 값 저장
			for (int i = 0; i < base2.length(); i++) {
				char[] c = base2.toCharArray();
				if (c[i] == '0')
					c[i] = '1';
				else
					c[i] = '0';

				list.add(base2ToDec(new String(c)));
			}
			// 3진수 입력 값에서 한 자리씩 바꿔가며 모든 경우를 탐색하며 이미 2진수에서 나올 수 있는 경우와 비교하여 같은 값이 나오면  for문 종료
			loop: for (int i = 0; i < base3.length(); i++) {
				for (int j = 0; j < 3; j++) {
					char[] c = base3.toCharArray();
					if ((c[i] - '0') == j)
						continue;

					c[i] = (char) ('0' + j);

					long num = base3ToDec(new String(c));
					// 2진수에서 찾은 값과 일치하는 값이면 종료
					if (list.contains(num)) {
						answer = num;
						break loop;
					}
				}
			}
			// 결과 출력
			System.out.format("#%d %d\n", tc, answer);
		}

		br.close();
	}
	// 2진수 String을 입력받아 10진수 형태로 바꿔준다
	public static long base2ToDec(String base2) {
		long num = 0;

		long bit = 1;
		for (int i = base2.length() - 1; i >= 0; i--) {
			if (base2.charAt(i) == '1')
				num += bit;
			bit *= 2;
		}

		return num;
	}
	// 3진수 String을 입력받아 10진수 형태로 바꿔준다
	public static long base3ToDec(String base3) {
		long num = 0;

		long bit = 1;

		for (int i = base3.length() - 1; i >= 0; i--) {
			if (base3.charAt(i) != '0')
				num += (base3.charAt(i) - '0') * bit;
			bit *= 3;
		}

		return num;
	}
}
