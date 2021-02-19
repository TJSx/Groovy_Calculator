def calculate() {
	def quit_trigger = false
	while (quit_trigger == false) {
		//initialize variables
		def numbers = []
		def operators = []
		def temp = []
		def valid = true
		//pointers for the current calculation
		p1 = 0
		p2 = 1
		op = 0
		
		//get valid input
		println("please enter an equation to solve type quit to exit out of program:")
		//take equation from user
		//remove all whitespace
		def equation_string = System.in.newReader().readLine()
		equation = equation_string.toLowerCase()
		equation = equation.replaceAll(' ', "")

		//separate and store numbers and operators
		numbers = equation.findAll(/(?<=\D|^)[\(]?(-)?[0-9]*[.]?[0-9]+[\)]?/ )*.toDouble()
		temp = equation.split((/(?<=\D|^)(-)?[0-9]*[.]?[0-9]+/ ))
		operators = temp.findAll { it != '' }
		
		//input validation check -- catches duplicate operators and words
		for(i = 0; i < operators.size(); i++) {
			if(operators[i] == 'quit' || operators[i] == 'Quit')
			{
				quit_trigger = true
				valid = false
			}
			if(operators[i].find(/[a-zA-Z]/)) {
				print("Cannot calculate - letters cannot be used")
				valid = false
			}
			else if(operators[i].size() > 1) {
				print("cannot calculate " + operators[i] + " is invalid formatting")
				valid = false
			}
		}

		/*
		 * Main calculator logic
		 */
		def k = 1
		def i = 0

		//end up with 1 number
		//finds 1st valid operation - does it - then repeats from beginning
		//left to right math
		while(k != numbers.size() && valid == true) {
			p1 = 0
			p2 = 1
			switch(operators[i]) {
				case '+':
				// * and / preference check - checks next operation
					if(i + 1 < operators.size()) {
						if(operators[i+1] == '*') {
							println("multplication preference")
							p1++
							p2++
							new_temp = numbers[p1] * numbers[p2]
							numbers[p1] = new_temp
							numbers.remove(p2)
							operators.remove(i+1)
						}
						else if(operators[i + 1] == '/') {
							p1++
							p2++
							new_temp = numbers[p1] / numbers[p2]
							numbers[p1] = new_temp
							numbers.remove(p2)
							operators.remove(i+1)
						}
						else if(operators[i + 1] == '(') {
							p1++
							p2++
							new_temp = numbers[p1] / numbers[p2]
							numbers[p1] = new_temp
							numbers.remove(p2)
							operators.remove(i+1)
						}
						else {
							new_temp = numbers[p1] + numbers[p2]
							numbers[p1] = new_temp
							numbers.remove(p2)
							operators.remove(i)
						}
					}
					else {
						new_temp = numbers[p1] + numbers[p2]
						numbers[p1] = new_temp
						numbers.remove(p2)
						operators.remove(i)

					}
					break;
				case '-':
				// preference check
					if(i + 1 < operators.size()) {
						if(operators[i+1] == '*') {
							p1++
							p2++
							new_temp = numbers[p1] * numbers[p2]
							numbers[p1] = new_temp
							numbers.remove(p2)
							operators.remove(i+1)
						}
						else if(operators[i + 1] == '/') {
							p1++
							p2++
							new_temp = numbers[p1] / numbers[p2]
							numbers[p1] = new_temp
							numbers.remove(p2)
							operators.remove(i+1)
						}
						else {
							new_temp = numbers[p1] - numbers[p2]
							numbers[p1] = new_temp
							numbers.remove(p2)
							operators.remove(i)

						}
					}
					else {
						new_temp = numbers[p1] - numbers[p2]
						numbers[p1] = new_temp
						numbers.remove(p2)
						operators.remove(i)

					}
					break;
				case "*":
					new_temp = numbers[p1] * numbers[p2]
					numbers[p1] = new_temp
					numbers.remove(p2)
					operators.remove(i)
					break;
				case "/":
					new_temp = numbers[p1] / numbers[p2]
					numbers[p1] = new_temp
					numbers.remove(p2)
					operators.remove(i)
					break;
				default:
					println("The value is unknown");
					k = numbers.size()
					break;
			}
		}
		return(numbers[0])
	}
}

def main() {
	answer = calculate()
	println(answer)
}

main()