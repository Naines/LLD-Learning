## Browser History

# Assumptions:

Each tab maintains separate history.
Visiting a new page clears forward history.
Ignore bookmarks/downloads.
History size is limited.

# APIs:

void visit(String url)
String back(int steps)
String forward(int steps)
String getCurrentPage()

# Focus Areas:

Efficient back/forward navigation.
History persistence.
Multi-tab support.
Memory-efficient design.