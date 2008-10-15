;Assuming Steppers bit 0 = right stepper motor step, bit 1 = right stepper motor direction ( 1 = forward ),
;  bit 2 = left stepper motor step, bit 3 = left stepper motor direction ( 1 = forward )
;v1.1
	
	org 0200h
	
;*********************************
; Move forward 1000 steps.

;Set step count to 1000d.
	lda #03h
	sta StepCount+1d
	lda #0e8h
	sta StepCount
	
;Place both motors in forward state.
	lda Steppers
	ora #00001010b
	sta Steppers


MoveForward *
;Step both motors once by giving them both a pulse.
	lda Steppers
	ora #00000101b
	sta Steppers
	
	lda Steppers
	and #11111010b
	sta Steppers

	
;Motor speed control.
	jsr Delay
	

;Decrement the step count.
	jsr DecStepCount
	
	bcc MoveForward
	
;*********************************
; Turn left.

;Set step count to 300d.
	lda #01h
	sta StepCount+1d
	lda #02ch
	sta StepCount
	
;Place left motor in reverse..
	lda Steppers
	and #11110111b
	sta Steppers
	
TurnLeft *	

;Step both motors once by giving them both a pulse.
	lda Steppers
	ora #00000101b
	sta Steppers
	
	lda Steppers
	and #11111010b
	sta Steppers

	
;Motor speed control.
	jsr Delay
	

;Decrement the step count.
	jsr DecStepCount
	
	bcc TurnLeft
	
;**********************************
	
	
	brk
	
;**********************************
;Can make the delay longer as needed.
Delay *

	ldx #0c0h
WasteLoop *
	dex
	bne WasteLoop
	
	rts
	
	
;**********************************
	
DecStepCount *
	dec StepCount
	bne NotZero
	lda StepCount+1d
	beq Zero
	dec StepCount+1d
	
NotZero *
	clc
	rts
	
Zero *
	sec
	rts
	
	
;**********************************
;Variables
StepCount dwd 0d
Steppers dbt 0d
	
	end
	
