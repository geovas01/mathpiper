INPUT	    	equ 4000h
OUTPUT	    	equ 4001h
LCDWRITE    	equ 4002h

	org 0200h
	
	lda #IRQHndlr<		
	sta 0002h
	lda #IRQHndlr>
	sta 0003h
	
	lda #10d
	sta counter
	
	cli
	
top *
	jmp top
	
	
IRQHndlr *
	sei
	
	dec counter
	bne Not1Sec
	
	ldx #10d
	stx counter
	
	lda toggle
	cmp #1d
	
	beq TurnOff
	
TurnOn	*
	lda #0ffh
	sta OUTPUT
	inc toggle
	jmp Return
	
TurnOff *
	lda #00h
	sta OUTPUT
	dec toggle
	

Not1Sec *
Return *
	
	cli
	rti

counter dbt ?
toggle dbt 0d
	end
	
