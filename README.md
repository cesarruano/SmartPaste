# SmartPaste
This is an Eclipse plugin to make numeric progressions in a copy/paste way.

for example:

      int v0 = 0u;
      int v1 = 2u;

smart pastes to:

      int v2 = 4u;

The next number is automatically deduced from the value in the selected text and previous text.
It also respects zeros at the left. 
If no text is selected it will take the current line.

Just drag and drop the .jar into your dropins folder and press ctrl+9.
