;The purpose of this program is to calculate the
;sum of the array nums and then to place the
;result into the variable sum.
;v1.1

	org 0200h
	
;An array of 10 bytes.
nums	dbt 1d,2d,3d,4d,5d,6d,7d,8d,9d,10d

;Holds the sum of array at nums.
sum	dbt 0d
	
	org 0250h
	
;Initialize the X register so that it offsets 0 
;positions into the array nums.
	ldx #0d
	
;Initialize register 'A' to 0.  This needs to be done 
;so that an old value in 'A' does not produce a wrong 
;sum during the first loop iteration.
	lda #0d
	
;Clear the carry flag so that it does not cause a
;wrong sum to be cacluated by the ADC instruction.
	clc
	
;This label is the top of the calculation loop.
AddMore  *

;Obtain a value from the array at offset X positions 
;into the array and add this value to the contents 
;of the 'A' register.
	adc nums,x
	
;Increment X to the next offset position.
	inx
	
;If X has been incremented to 10, fall through the 
;bottom of the loop.  If X is less than 10 then loop 
;back to AddMore and add another value from the array.
	cpx #10d
	bne AddMore
	
;After the loop has finished calculating the sum of 
;the array, store this sum into the variable called 
;'sum'.
	sta sum
	
;Return program control back to the monitor.
	brk
	
	end
	
