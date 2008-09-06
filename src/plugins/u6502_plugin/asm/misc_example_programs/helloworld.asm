INPUTS	    equ 0800h
OUTPUTS	    equ 0801h
LCDWRITE    equ 0802h
L000F0      equ 00F0h
L08001      equ 8001h

            org 0200h


	    jsr Hello6502	
	    jmp pollInputs

Hello6502 *
	    lda #'H'
	    sta LCDWRITE
	    lda #'e'
	    sta LCDWRITE
	    lda #'l'
	    sta LCDWRITE
	    lda #'l'
	    sta LCDWRITE
	    lda #'o'
	    sta LCDWRITE
	    lda #'6'
	    sta LCDWRITE
	    lda #'5'
	    sta LCDWRITE
	    lda #'0'
	    sta LCDWRITE
	    lda #'2'
	    sta LCDWRITE
	    lda #0Ah
	    sta LCDWRITE
	    lda #00h
	    rts
	    
pollInputs *
		lda INPUTS
		sta OUTPUTS
		jmp pollInputs
			
 
 
            end

