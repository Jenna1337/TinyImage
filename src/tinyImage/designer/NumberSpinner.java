package tinyImage.designer;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class NumberSpinner extends JSpinner
{
	public NumberSpinner(int value, int minimum, int maximum, int stepSize, int length)
	{
		super(new SpinnerNumberModel(value, minimum, maximum, stepSize));
		((JSpinner.NumberEditor)this.getEditor()).getTextField().setDocument(new javax.swing.text.PlainDocument()
		{
			public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException
			{
				if (str == null)
					return;
				System.out.println(str);
				try{
					Integer.parseInt(str);
					if ((getLength() + str.length()) <= length)
						super.insertString(offset, str, attr);
				} catch(NumberFormatException nfe) {
				}
			}
		});
		/*((javax.swing.text.AbstractDocument)((JSpinner.NumberEditor)this.getEditor()).getTextField().getDocument()).setDocumentFilter(
			);*/
	}
}
