package testcases.C0048_MISSBREAK;

public class C0048_MISSBREAK_01 {
	public void bad(int a) {
		switch (a) {
		case 1:
		case 2:
			break;
		case 3:
			return;
		case 4: // FLAW
			a = 4;
		case 5:
			a = 5;
			break;
		case 6: // FLAW
			if (a == 0)
				break;
		case 7:
			if (a == 0);
			break;
		case 8: { // FLAW
			if (a == 0)
				break;
		}
		case 9: {
			if (a == 0)
				break;
		}
		break;
		default:
			a = -1;
		}
	}

	public void good(int a) {
		switch (a) {
		case 1:
		case 2:
			break;
		case 3:
			return;
		case 4:
			a = 4;
			break; // FIX
		case 5:
			a = 5;
			break;
		case 6:
			if (a == 0)
				break;
			else
				break; // FIX
		case 7:
			if (a == 0);
			break;
		case 8: {
			if (a == 0)
				break;
		}
		break; // FIX
		case 9: {
			if (a == 0)
				break;
		}
		break;
		default:
			a = -1;
		}
	}
}
